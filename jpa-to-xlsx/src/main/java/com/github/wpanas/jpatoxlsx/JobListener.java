package com.github.wpanas.jpatoxlsx;

import com.github.wpanas.jpatoxlsx.model.Book;
import com.github.wpanas.jpatoxlsx.model.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import javax.batch.runtime.BatchStatus;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static javax.batch.runtime.BatchStatus.COMPLETED;

@Slf4j
public class JobListener implements JobExecutionListener {
    private final SXSSFWorkbook workbook;
    private final BookRepository bookRepository;
    private final FileOutputStream fileOutputStream;

    JobListener(SXSSFWorkbook workbook, FileOutputStream fileOutputStream, BookRepository bookRepository) {
        this.workbook = workbook;
        this.bookRepository = bookRepository;
        this.fileOutputStream = fileOutputStream;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        BatchStatus batchStatus = jobExecution.getStatus().getBatchStatus();
        if (batchStatus == COMPLETED) {
            try {
                workbook.write(fileOutputStream);
                fileOutputStream.close();
                workbook.dispose();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        initializeBooks(bookRepository);
    }

    private void initializeBooks(BookRepository bookRepository) {
        Set<Book> books = new HashSet<>();
        Book.BookBuilder builder = Book.builder();
        books.add(builder.author("John Doe").title("Forbbiden tails").isbn("1111-111-111-111").build());
        books.add(builder.author("Mary Doe").title("Not found title").isbn("2222-222-222-222").build());
        bookRepository.saveAll(books);
    }
}
