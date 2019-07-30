package com.hotmart.playground.es;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotmart.playground.es.Ideadoc;
import com.hotmart.playground.es.IdeadocRepository;

@Service
public class IdeadocService {
	
	private final IdeadocRepository ideadocRepository;
	
	@Autowired
	public IdeadocService(IdeadocRepository ideadocRepository) {
		this.ideadocRepository = ideadocRepository;
		
	}
	
	public Ideadoc save(Ideadoc doc) {
		return ideadocRepository.save(doc);
	}
	
	public long count() {
		return ideadocRepository.count();
	}

}
