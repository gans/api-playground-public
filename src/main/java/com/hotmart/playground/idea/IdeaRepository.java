package com.hotmart.playground.idea;

import com.hotmart.playground.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.List;

public interface IdeaRepository extends PagingAndSortingRepository<Idea, Long> {

    @Query(value = IdeaRepositoryQueries.FIND_BY_CATEGORY_QUERY, countQuery = IdeaRepositoryQueries.FIND_BY_CATEGORY_COUNT_QUERY)
    Page<IdeaDto> findByCategoryId(@Param("category") @Nullable final Long category,
                                   @Param("user") final User user,
                                   final Pageable pageable);

    @Query(IdeaRepositoryQueries.FIND_NEWEST_IDEAS_QUERY)
    List<IdeaDto> findNewestIdeas(@Param("user") final User user,
                                  final Pageable pageable);

    @Query(IdeaRepositoryQueries.FIND_POPULAR_IDEAS_QUERY)
    List<IdeaDto> findPopularIdeas(@Param("user") final User user,
                                   final Pageable pageable);
}
