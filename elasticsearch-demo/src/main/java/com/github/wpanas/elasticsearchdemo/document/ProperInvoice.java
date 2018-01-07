package com.github.wpanas.elasticsearchdemo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.util.Set;

@Data
@Document(indexName = "proper-invoice")
public class ProperInvoice {
    @Id
    @Field(type = FieldType.String)
    private String id;
    @Field(type = FieldType.String)
    private String number;
    @Field(type = FieldType.Object)
    private LocalDate saleDate;
    @Field(type = FieldType.Nested)
    private Set<ProperInvoicePosition> invoicePositions;
}
