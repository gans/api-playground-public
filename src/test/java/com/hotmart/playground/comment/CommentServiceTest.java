package com.hotmart.playground.comment;

import com.hotmart.playground.idea.UpdateIdeaStatsEvent;
import com.hotmart.playground.user.User;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, RandomBeansExtension.class})
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private CommentService commentService;

    @Test
    public void delete_DeleteCommentAndUpdateIdeaStats(@Random Comment comment) {
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        commentService.delete(comment, comment.getUser());

        verify(commentRepository).delete(any());
        verify(applicationEventPublisher).publishEvent(any(UpdateIdeaStatsEvent.class));
    }

    @Test
    public void delete_ThrowAccessDeniedException_IfLoggedUserIsNotCommentOwner(@Random Comment comment,
                                                                                @Random User user) {

        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        assertThrows(AccessDeniedException.class, () ->
                commentService.delete(comment, user)
        );
    }

    @Test
    public void delete_ThrowEntityNotFoundException_IfCommentNotExists(@Random Comment comment) {

        when(commentRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                commentService.delete(comment, comment.getUser())
        );
    }
}
