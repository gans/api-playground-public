package com.hotmart.playground.user;

import com.hotmart.playground.validation.ValidPassword;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
public class UserCreationDto {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @ValidPassword
    private String password;

    @NotEmpty
    private String passwordConfirmation;

    @NotNull
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private LocalDateTime createAt = LocalDateTime.now();

    @NotEmpty
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String role = "USER";

    @AssertTrue
    private boolean isPasswordEqualsPasswordConfirmation() {
        return Optional.ofNullable(passwordConfirmation).orElse("").equals(password);
    }
}
