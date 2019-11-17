package com.github.wpanas.jpatoxlsx;

import com.github.wpanas.jpatoxlsx.model.Book;
import com.github.wpanas.jpatoxlsx.model.BookRepository;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static java.util.Collections.singletonMap;
import static org.springframework.data.domain.Sort.Direction.ASC;

@Configuration
@EnableBatchProcessing
@EnableJpaRepositories
public class BatchConfiguration {
    private static final Integer CHUNK = 100;
    private static final String EXPORT_FILENAME = "/tmp/export.xlsx";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Workbook workbook() {
        return new SXSSFWorkbook(CHUNK);
    }

    @Bean
    public FileOutputStream fileOutputStream() throws FileNotFoundException {
        return new FileOutputStream(EXPORT_FILENAME);
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
        reader.setSort(singletonMap("id", ASC));
        return reader;
    }

    @Bean
    public ItemWriter<Book> bookWriter(SXSSFWorkbook workbook) {
        SXSSFSheet sheet = workbook.createSheet("Books");
        return new BookWriter(sheet);
    }

    @Bean
    JobListener jobListener(SXSSFWorkbook workbook, FileOutputStream fileOutputStream, BookRepository bookRepository) throws IOException {
        return new JobListener(workbook, fileOutputStream, bookRepository);
    }
}
