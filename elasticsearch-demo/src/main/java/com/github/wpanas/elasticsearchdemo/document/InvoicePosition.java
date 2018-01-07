package com.github.wpanas.elasticsearchdemo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;

@Data
@Document(indexName = "invoice-positions")
public class InvoicePosition {
    @Id
    private String id;
    private String name;
    private BigDecimal price;
    private BigDecimal quantity;
    private String unit;
}
