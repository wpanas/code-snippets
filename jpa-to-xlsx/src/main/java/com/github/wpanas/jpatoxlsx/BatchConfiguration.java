package com.github.wpanas.jpatoxlsx;

import com.github.wpanas.jpatoxlsx.model.Book;
import com.github.wpanas.jpatoxlsx.model.BookRepository;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

@Configuration
@EnableBatchProcessing
@EnableJpaRepositories
public class BatchConfiguration {
    private static Integer CHUNK = 100;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public SXSSFWorkbook workbook() {
        return new SXSSFWorkbook(CHUNK);
    }

    @Bean
    public Job job(Step step, JobExecutionListener listener) {
        return jobBuilderFactory.get("exportBooksToXlsx")
                .start(step)
                .listener(listener)
                .build();
    }

    @Bean
    public Step step(ItemReader<Book> reader, ItemWriter<Book> writer) {
        return stepBuilderFactory.get("export")
                .<Book, Book>chunk(CHUNK)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<Book> bookReader(BookRepository repository) {
        RepositoryItemReader<Book> reader = new RepositoryItemReader<>();
        reader.setRepository(repository);
        reader.setMethodName("findAll");
        reader.setPageSize(CHUNK);
        return reader;
    }

    @Bean
    public ItemWriter<Book> bookWriter(SXSSFWorkbook workbook) {
        return new BookWriter(workbook);
    }

    @Bean
    JobListener jobListener(SXSSFWorkbook workbook, BookRepository bookRepository) throws IOException {
        return new JobListener(workbook, bookRepository);
    }
}
