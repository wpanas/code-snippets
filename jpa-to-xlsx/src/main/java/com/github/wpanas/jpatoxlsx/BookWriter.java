package com.github.wpanas.jpatoxlsx;

import com.github.wpanas.jpatoxlsx.model.Book;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.batch.item.ItemWriter;

import java.util.List;


public class BookWriter implements ItemWriter<Book> {
    private final SXSSFSheet sheet;
    private Integer currentRowNumber;
    private Integer currentColumnNumber;
    private SXSSFRow row;

    BookWriter(SXSSFWorkbook workbook) {
        this.sheet = workbook.createSheet("Books");
        this.currentRowNumber = 0;
        this.currentColumnNumber = 0;
    }

    @Override
    public void write(List<? extends Book> list) {
        list.forEach(this::writeBook);
    }

    private void writeBook(Book book) {
        addRow();
        addCell(book.getId().toString());
        addCell(book.getAuthor());
        addCell(book.getTitle());
        addCell(book.getIsbn());
    }

    private void addRow() {
        row = this.sheet.createRow(currentRowNumber);
        currentRowNumber++;
        currentColumnNumber = 0;
    }

    private void addCell(String value) {
        SXSSFCell cell = row.createCell(currentColumnNumber);
        cell.setCellValue(value);
        currentColumnNumber++;
    }
}
