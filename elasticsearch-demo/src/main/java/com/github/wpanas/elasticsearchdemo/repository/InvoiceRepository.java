package com.github.wpanas.elasticsearchdemo.repository;

import com.github.wpanas.elasticsearchdemo.document.Invoice;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends ElasticsearchCrudRepository<Invoice, String> {
}
