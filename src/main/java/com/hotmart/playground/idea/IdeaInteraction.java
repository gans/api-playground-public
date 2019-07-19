package com.hotmart.playground.idea;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hotmart.playground.social.Interaction;
import com.hotmart.playground.social.InteractionType;
import com.hotmart.playground.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdeaInteraction extends Interaction {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_idea")
    private Idea idea;

    @Builder
    public IdeaInteraction(Long id, @NotNull User user, @NotNull InteractionType type, @NotNull LocalDateTime createAt, @NotNull Idea idea) {
        super(id, user, type, createAt);
        this.idea = idea;
    }
}
