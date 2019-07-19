package com.hotmart.playground.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentCreationDto {

    @NotEmpty
    private String message;

    @NotNull
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private LocalDateTime createAt = LocalDateTime.now();
}
