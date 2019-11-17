package com.github.wpanas.jpatoxlsx;

import com.github.wpanas.jpatoxlsx.model.Book;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;

public class BookWriterTest {
    private BookWriter underTest;
    private SXSSFSheet sheet;

    @Before
    public void setUp() {
        sheet = mock(SXSSFSheet.class);
        underTest = new BookWriter(sheet);
    }

    @Test
    public void name() {
        // given
        Book harryPotter = Book.builder()
                .id(1L)
                .title("Harry Potter")
                .author("J. K. Rowling")
                .isbn("1234")
                .build();
        List<? extends Book> books = singletonList(harryPotter);
        SXSSFRow row = mock(SXSSFRow.class);

        when(sheet.createRow(0)).thenReturn(row);
        SXSSFCell firstCell = mock(SXSSFCell.class);
        when(row.createCell(0)).thenReturn(firstCell);
        SXSSFCell secondCell = mock(SXSSFCell.class);
        when(row.createCell(1)).thenReturn(secondCell);
        SXSSFCell thirdCell = mock(SXSSFCell.class);
        when(row.createCell(2)).thenReturn(thirdCell);
        SXSSFCell forthCell = mock(SXSSFCell.class);
        when(row.createCell(3)).thenReturn(forthCell);

        // when
        underTest.write(books);

        // then
        verify(firstCell).setCellValue("1");
        verify(secondCell).setCellValue("J. K. Rowling");
        verify(thirdCell).setCellValue("Harry Potter");
        verify(forthCell).setCellValue("1234");
    }
}