package com.hotmart.playground.idea;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IdeaStatsDto {

    private long likes;

    private long dislikes;

    private long joins;

    private long comments;
}
