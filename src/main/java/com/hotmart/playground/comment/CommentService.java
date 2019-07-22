package com.hotmart.playground.comment;

import com.hotmart.playground.common.EntityAction;
import com.hotmart.playground.idea.UpdateIdeaStatsEvent;
import com.hotmart.playground.social.InteractionType;
import com.hotmart.playground.user.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public CommentService(CommentRepository commentRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.commentRepository = commentRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Page<Comment> findAllByIdea(final Long idea, final Pageable pageable) {
        return commentRepository.findAllByIdeaIdOrderByCreateAtDesc(idea, pageable);
    }

    @Transactional
    public Comment save(final Comment comment) {
    	commentRepository.save(comment);
    	applicationEventPublisher.publishEvent(
    			UpdateIdeaStatsEvent.builder()
    				.source(this)
    				.type(InteractionType.COMMENT)
    				.idea(comment.getIdea())
    				.user(comment.getUser())
    				.action(EntityAction.CREATE)
    				.build()
    			);
        return comment;
    }

    @Transactional
    public void delete(final Comment comment, final User user) {
        Comment commentToDelete = commentRepository.findById(comment.getId())
                .orElseThrow(() -> new EntityNotFoundException("Comment " + comment.getId() + " not found"));

        if (user.equals(commentToDelete.getUser())) {
            commentRepository.delete(commentToDelete);
            applicationEventPublisher.publishEvent(
                    UpdateIdeaStatsEvent.builder()
                            .source(this)
                            .type(InteractionType.COMMENT)
                            .idea(comment.getIdea())
                            .user(commentToDelete.getUser())
                            .action(EntityAction.DELETE)
                            .build()
            );
        } else {
            throw new AccessDeniedException("User is not owner of comment " + comment.getId());
        }
    }
}
