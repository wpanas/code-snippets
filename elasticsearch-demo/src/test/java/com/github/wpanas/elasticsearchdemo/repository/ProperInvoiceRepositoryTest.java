package com.github.wpanas.elasticsearchdemo.repository;

import com.github.wpanas.elasticsearchdemo.document.ProperInvoice;
import com.github.wpanas.elasticsearchdemo.document.ProperInvoicePosition;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProperInvoiceRepositoryTest {
    @Autowired
    private ProperInvoiceRepository repository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    private ProperInvoice properInvoice;

    @Before
    public void setUp() {
        properInvoice = new ProperInvoice();
        properInvoice.setId("1");
        properInvoice.setNumber("001");
        properInvoice.setSaleDate(LocalDate.of(2017, 12, 27));

        ProperInvoicePosition position1 = new ProperInvoicePosition();
        position1.setId("1");
        position1.setName("Apple");
        position1.setPrice(BigDecimal.valueOf(1.25));
        position1.setQuantity(BigDecimal.valueOf(2));
        position1.setUnit("pcs");

        ProperInvoicePosition position2 = new ProperInvoicePosition();
        position2.setId("2");
        position2.setName("Water");
        position2.setPrice(BigDecimal.valueOf(2));
        position1.setQuantity(BigDecimal.valueOf(1.25));
        position1.setUnit("litre");

        Set<ProperInvoicePosition> positions = new HashSet<>(Arrays.asList(position1, position2));
        properInvoice.setInvoicePositions(positions);

        elasticsearchTemplate.createIndex(ProperInvoice.class);
        elasticsearchTemplate.createIndex(ProperInvoicePosition.class);

        log.info(properInvoice.toString());
    }

    @Test
    public void save() {
        repository.save(properInvoice);
    }
}