package com.hotmart.playground.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {

    Page<Comment> findAllByIdeaIdOrderByCreateAtDesc(Long idea, Pageable pageable);
}
