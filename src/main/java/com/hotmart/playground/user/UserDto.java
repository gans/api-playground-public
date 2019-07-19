package com.hotmart.playground.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private Long id;

    private String email;
}
