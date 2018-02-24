package com.github.wpanas.jpatoxlsx;

import com.github.wpanas.jpatoxlsx.model.Book;
import com.github.wpanas.jpatoxlsx.model.BookRepository;
import lombok.extern.log4j.Log4j;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import javax.batch.runtime.BatchStatus;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Log4j
public class JobListener implements JobExecutionListener {
    private final SXSSFWorkbook workbook;
    private final FileOutputStream outputStream;

    public JobListener(SXSSFWorkbook workbook, BookRepository bookRepository) throws IOException {
        this.workbook = workbook;
        this.outputStream = new FileOutputStream("/tmp/export.xlsx");

        initializeBooks(bookRepository);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        BatchStatus batchStatus = jobExecution.getStatus().getBatchStatus();
        if (batchStatus.equals(BatchStatus.COMPLETED)) {
            try {
                this.workbook.write(outputStream);
                outputStream.close();
                workbook.dispose();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private void initializeBooks(BookRepository bookRepository) {
        Set<Book> bookSet = new HashSet<>();
        Book.BookBuilder builder = Book.builder();
        bookSet.add(builder.author("John Doe").title("Forbbiden tails").isbn("1111-111-111-111").build());
        bookSet.add(builder.author("Mary Doe").title("Not found title").isbn("2222-222-222-222").build());
        bookRepository.save(bookSet);
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }
}
