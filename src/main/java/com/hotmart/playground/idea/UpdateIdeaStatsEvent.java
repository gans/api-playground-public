package com.hotmart.playground.idea;

import com.hotmart.playground.common.EntityAction;
import com.hotmart.playground.social.InteractionType;
import com.hotmart.playground.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateIdeaStatsEvent extends ApplicationEvent {

    private Idea idea;

    private User user;

    private InteractionType type;

    private EntityAction action;

    @Builder
    public UpdateIdeaStatsEvent(Object source, Idea idea, User user, InteractionType type, EntityAction action) {
        super(source);
        this.idea = idea;
        this.user = user;
        this.type = type;
        this.action = action;
    }
}
