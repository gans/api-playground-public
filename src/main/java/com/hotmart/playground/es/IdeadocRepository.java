package com.hotmart.playground.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface IdeadocRepository extends ElasticsearchRepository<Ideadoc, String> {

	
}
