package com.github.wpanas.elasticsearchdemo.repository;

import com.github.wpanas.elasticsearchdemo.document.InvoicePosition;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

public interface InvoicePositionRepository extends ElasticsearchCrudRepository<InvoicePosition, String> {
}
