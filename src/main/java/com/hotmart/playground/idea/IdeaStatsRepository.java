package com.hotmart.playground.idea;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface IdeaStatsRepository extends PagingAndSortingRepository<IdeaStats, Long> {

    @Modifying
    @Query("UPDATE IdeaStats s SET s.likes = s.likes + :quantity WHERE s.idea = :idea AND s.likes + :quantity >= 0")
    void incrementLikes(@Param("idea") final Idea idea, @Param("quantity") final long quantity);

    @Modifying
    @Query("UPDATE IdeaStats s SET s.dislikes = s.dislikes + :quantity WHERE s.idea = :idea AND s.dislikes + :quantity >= 0")
    void incrementDislikes(@Param("idea") final Idea idea, @Param("quantity") final long quantity);

    @Modifying
    @Query("UPDATE IdeaStats s SET s.joins = s.joins + :quantity WHERE s.idea = :idea AND s.joins + :quantity >= 0")
    void incrementJoins(@Param("idea") final Idea idea, @Param("quantity") final long quantity);

    @Modifying
    @Query("UPDATE IdeaStats s SET s.comments = s.comments + :quantity WHERE s.idea = :idea AND s.comments + :quantity >= 0")
    void incrementComments(@Param("idea") final Idea idea, @Param("quantity") final long quantity);
}
