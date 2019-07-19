package com.hotmart.playground.category;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryDto {

    private Long id;

    private String name;

    private String description;

    private String image;
}
