package com.hotmart.playground.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hotmart.playground.common.BusinessEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements BusinessEntity {

    private static final long serialVersionUID = -8463432170697434282L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @JsonIgnore
    private String password;

    @NotEmpty
    @JsonIgnore
    private String role;

    @NotNull
    @JsonIgnore
    private LocalDateTime createAt;

    public User(Long id) {
        this.id = id;
    }
}
