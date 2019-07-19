package com.hotmart.playground.social;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class UserInteractionDto {

    private InteractionType type;

    private LocalDateTime createAt;
}
