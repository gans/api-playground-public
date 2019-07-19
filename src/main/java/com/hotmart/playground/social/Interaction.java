package com.hotmart.playground.social;

import com.hotmart.playground.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class Interaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private InteractionType type;

    @NotNull
    private LocalDateTime createAt;
}
