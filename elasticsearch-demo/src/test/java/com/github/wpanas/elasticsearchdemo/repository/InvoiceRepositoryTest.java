package com.github.wpanas.elasticsearchdemo.repository;

import com.github.wpanas.elasticsearchdemo.document.Invoice;
import com.github.wpanas.elasticsearchdemo.document.InvoicePosition;
import lombok.extern.java.Log;
import org.elasticsearch.index.mapper.MapperParsingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceRepositoryTest {
    @Autowired
    private InvoiceRepository repository;
    private Invoice invoice;

    @Before
    public void setUp() {
        invoice = new Invoice();
        invoice.setId("1");
        invoice.setNumber("001");
        invoice.setSaleDate(LocalDate.of(2017, 12, 27));

        InvoicePosition position1 = new InvoicePosition();
        position1.setId("1");
        position1.setName("Apple");
        position1.setPrice(BigDecimal.valueOf(1.25));
        position1.setQuantity(BigDecimal.valueOf(2));
        position1.setUnit("pcs");

        InvoicePosition position2 = new InvoicePosition();
        position2.setId("2");
        position2.setName("Water");
        position2.setPrice(BigDecimal.valueOf(2));
        position1.setQuantity(BigDecimal.valueOf(1.25));
        position1.setUnit("litre");

        Set<InvoicePosition> positions = new HashSet<>(Arrays.asList(position1, position2));
        invoice.setInvoicePositions(positions);

        log.info(invoice.toString());
    }

    @Test
    public void save() {
        boolean thrown = false;

        try {
            repository.save(invoice);
        } catch (MapperParsingException exception) {
            thrown = true;
            log.info(exception.getDetailedMessage());
        }

        assertTrue(thrown);
    }
}