package com.hotmart.playground.idea;

import com.hotmart.playground.category.CategoryDto;
import com.hotmart.playground.social.UserInteractionDto;
import com.hotmart.playground.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class IdeaDto {

    private Long id;

    private UserDto user;

    private CategoryDto category;

    private IdeaStatsDto stats;

    private String title;

    private String description;

    private LocalDateTime createAt;

    private List<UserInteractionDto> userInteractions;

    public IdeaDto(Long id,
                   String title,
                   String description,
                   LocalDateTime createAt,
                   Long userId,
                   String userEmail,
                   Long categoryId,
                   String categoryName,
                   String categoryDescription,
                   String categoryImage,
                   long ideaStatsLikes,
                   long ideaStatsDislikes,
                   long ideaStatsJoins,
                   long ideaStatsComments,
                   IdeaInteraction likedOrDisliked,
                   IdeaInteraction joined) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.createAt = createAt;
        this.user = UserDto.builder()
                .id(userId)
                .email(userEmail)
                .build();
        this.category = CategoryDto.builder()
                .id(categoryId)
                .name(categoryName)
                .description(categoryDescription)
                .image(categoryImage)
                .build();
        this.stats = IdeaStatsDto.builder()
                .likes(ideaStatsLikes)
                .dislikes(ideaStatsDislikes)
                .joins(ideaStatsJoins)
                .comments(ideaStatsComments)
                .build();

        List<UserInteractionDto> interactions = new ArrayList<>();

        if (likedOrDisliked != null) {
            interactions.add(UserInteractionDto.builder()
                    .createAt(likedOrDisliked.getCreateAt())
                    .type(likedOrDisliked.getType())
                    .build()
            );
        }

        if (joined != null) {
            interactions.add(UserInteractionDto.builder()
                    .createAt(joined.getCreateAt())
                    .type(joined.getType())
                    .build()
            );
        }

        this.userInteractions = Collections.unmodifiableList(interactions);
    }

}
