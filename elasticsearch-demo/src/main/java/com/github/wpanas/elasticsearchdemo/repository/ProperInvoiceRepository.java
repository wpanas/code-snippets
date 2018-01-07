package com.github.wpanas.elasticsearchdemo.repository;

import com.github.wpanas.elasticsearchdemo.document.ProperInvoice;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProperInvoiceRepository extends ElasticsearchCrudRepository<ProperInvoice, String> {
}
