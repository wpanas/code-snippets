package com.github.wpanas.jpatoxlsx;

import com.github.wpanas.jpatoxlsx.model.Book;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

import static java.util.Arrays.asList;


public class BookWriter implements ItemWriter<Book> {
    private final Sheet sheet;

    BookWriter(Sheet sheet) {
        this.sheet = sheet;
    }

    @Override
    public void write(List<? extends Book> list) {
        for (int i = 0; i < list.size(); i++) {
            writeRow(i, list.get(i));
        }
    }

    private void writeRow(int currentRowNumber, Book book) {
        List<String> columns = prepareColumns(book);
        Row row = this.sheet.createRow(currentRowNumber);
        for (int i = 0; i < columns.size(); i++) {
            writeCell(row, i, columns.get(i));
        }
    }

    private List<String> prepareColumns(Book book) {
        return asList(
                book.getId().toString(),
                book.getAuthor(),
                book.getTitle(),
                book.getIsbn()
        );
    }

    private void writeCell(Row row, int currentColumnNumber, String value) {
        Cell cell = row.createCell(currentColumnNumber);
        cell.setCellValue(value);
    }
}
