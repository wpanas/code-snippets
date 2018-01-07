package com.github.wpanas.elasticsearchdemo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDate;
import java.util.Set;

@Data
@Document(indexName = "invoice")
public class Invoice {
    @Id
    private String id;
    private String number;
    private LocalDate saleDate;
    private Set<InvoicePosition> invoicePositions;
}
