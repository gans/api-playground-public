package com.hotmart.playground.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<Category> findAll(final Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

}
