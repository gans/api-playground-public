package com.hotmart.playground.idea;

import com.hotmart.playground.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdeaService {

    private static final int TOP_IDEAS_FETCH_SIZE = 5;

    private final IdeaRepository ideaRepository;

    public IdeaService(final IdeaRepository ideaRepository) {
        this.ideaRepository = ideaRepository;
    }

    public Page<IdeaDto> findByCategory(@Nullable final Long category, final User user, final Pageable pageable) {
        return ideaRepository.findByCategoryId(category, user, pageable);
    }

    public Idea save(final Idea idea) {
        IdeaStats stats = IdeaStats.builder().idea(idea).build();
        idea.setStats(stats);
        return ideaRepository.save(idea);
    }

    public Page<IdeaDto> findNewestIdeas(final User user) {
        List<IdeaDto> newestIdeas = ideaRepository.findNewestIdeas(user, PageRequest.of(0, TOP_IDEAS_FETCH_SIZE));
        return new PageImpl<>(newestIdeas);
    }

    public Page<IdeaDto> findMostPopularIdeas(final User user) {
        List<IdeaDto> popularIdeas = ideaRepository.findPopularIdeas(user, PageRequest.of(0, TOP_IDEAS_FETCH_SIZE));
        return new PageImpl<>(popularIdeas);
    }
}
