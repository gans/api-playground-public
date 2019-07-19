package com.hotmart.playground.idea;

import com.hotmart.playground.common.EntityAction;
import com.hotmart.playground.social.InteractionType;
import com.hotmart.playground.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class IdeaInteractionService {

    private final IdeaInteractionRepository ideaInteractionRepository;

    private final IdeaStatsRepository ideaStatsRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public IdeaInteractionService(IdeaInteractionRepository ideaInteractionRepository, IdeaStatsRepository ideaStatsRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.ideaInteractionRepository = ideaInteractionRepository;
        this.ideaStatsRepository = ideaStatsRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    private void save(final Idea idea, final User user, final InteractionType type) {
        IdeaInteraction interaction = IdeaInteraction.builder()
                .idea(idea)
                .user(user)
                .type(type)
                .createAt(LocalDateTime.now())
                .build();

        ideaInteractionRepository.save(interaction);
    }

    @Transactional
    public void like(final Idea idea, final User user) {
        boolean alreadyLiked = ideaInteractionRepository.existsByIdeaAndUserAndType(idea, user, InteractionType.LIKE);
        if (alreadyLiked) {
            return;
        }

        save(idea, user, InteractionType.LIKE);
        applicationEventPublisher.publishEvent(
                UpdateIdeaStatsEvent.builder()
                        .source(this)
                        .idea(idea)
                        .user(user)
                        .type(InteractionType.LIKE)
                        .action(EntityAction.CREATE)
                        .build()
        );
    }

    @Transactional
    public void dislike(final Idea idea, final User user) {
        boolean alreadyDisliked = ideaInteractionRepository.existsByIdeaAndUserAndType(idea, user, InteractionType.DISLIKE);
        if (alreadyDisliked) {
            return;
        }


        save(idea, user, InteractionType.DISLIKE);
        applicationEventPublisher.publishEvent(
                UpdateIdeaStatsEvent.builder()
                        .source(this)
                        .idea(idea)
                        .user(user)
                        .type(InteractionType.DISLIKE)
                        .action(EntityAction.CREATE)
                        .build()
        );
    }

    @Transactional
    public void join(final Idea idea, final User user) {
        boolean alreadyJoined = ideaInteractionRepository.existsByIdeaAndUserAndType(idea, user, InteractionType.JOIN);
        if (alreadyJoined) {
            return;
        }

        save(idea, user, InteractionType.JOIN);
        applicationEventPublisher.publishEvent(
                UpdateIdeaStatsEvent.builder()
                        .source(this)
                        .idea(idea)
                        .user(user)
                        .type(InteractionType.JOIN)
                        .action(EntityAction.CREATE)
                        .build()
        );
    }

    @Transactional
    public void deleteLike(final Idea idea, final User user) {
        int deleteCount = ideaInteractionRepository.deleteByIdeaAndUserAndType(idea, user, InteractionType.LIKE);
        if (deleteCount > 0) {
            applicationEventPublisher.publishEvent(
                    UpdateIdeaStatsEvent.builder()
                            .source(this)
                            .idea(idea)
                            .user(user)
                            .type(InteractionType.LIKE)
                            .action(EntityAction.DELETE)
                            .build()
            );
        }
    }

    @Transactional
    public void deleteDislike(final Idea idea, final User user) {
        int deleteCount = ideaInteractionRepository.deleteByIdeaAndUserAndType(idea, user, InteractionType.DISLIKE);
        if (deleteCount > 0) {
            applicationEventPublisher.publishEvent(
                    UpdateIdeaStatsEvent.builder()
                            .source(this)
                            .idea(idea)
                            .user(user)
                            .type(InteractionType.DISLIKE)
                            .action(EntityAction.DELETE)
                            .build()
            );
        }
    }

    @Transactional
    public void deleteJoin(final Idea idea, final User user) {
        int deleteCount = ideaInteractionRepository.deleteByIdeaAndUserAndType(idea, user, InteractionType.JOIN);
        if (deleteCount > 0) {
            applicationEventPublisher.publishEvent(
                    UpdateIdeaStatsEvent.builder()
                            .source(this)
                            .idea(idea)
                            .user(user)
                            .type(InteractionType.JOIN)
                            .action(EntityAction.DELETE)
                            .build()
            );
        }
    }

    @Transactional
    @EventListener
    public void handleUpdateIdeaStatsEvent(UpdateIdeaStatsEvent event) {
        int quantity = 0;
        if (EntityAction.CREATE.equals(event.getAction())) {
            quantity = 1;
        } else if (EntityAction.DELETE.equals(event.getAction())) {
            quantity = -1;
        }

        switch (event.getType()) {
            case LIKE:
                ideaStatsRepository.incrementLikes(event.getIdea(), quantity);
                break;
            case DISLIKE:
                ideaStatsRepository.incrementDislikes(event.getIdea(), quantity);
                break;
            case JOIN:
                ideaStatsRepository.incrementJoins(event.getIdea(), quantity);
                break;
            case COMMENT:
                ideaStatsRepository.incrementComments(event.getIdea(), quantity);
                break;
            default:
                log.warn("Unsupported InteractionType: {}", event.getType());
        }
    }
}
