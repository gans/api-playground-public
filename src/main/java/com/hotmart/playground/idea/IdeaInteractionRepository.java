package com.hotmart.playground.idea;

import com.hotmart.playground.social.InteractionType;
import com.hotmart.playground.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IdeaInteractionRepository extends PagingAndSortingRepository<IdeaInteraction, Long> {

    boolean existsByIdeaAndUserAndType(final Idea idea, final User user, final InteractionType type);

    int deleteByIdeaAndUserAndType(final Idea idea, final User user, final InteractionType type);
}
