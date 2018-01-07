package com.github.wpanas.elasticsearchdemo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

@Data
@Document(indexName = "proper-invoice-positions")
public class ProperInvoicePosition {
    @Id
    @Field(type = FieldType.String)
    private String id;
    @Field(type = FieldType.String)
    private String name;
    @Field(type = FieldType.Double)
    private BigDecimal price;
    @Field(type = FieldType.Double)
    private BigDecimal quantity;
    @Field(type = FieldType.String)
    private String unit;
}
