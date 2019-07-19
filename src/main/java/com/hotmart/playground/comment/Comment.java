package com.hotmart.playground.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hotmart.playground.common.BusinessEntity;
import com.hotmart.playground.idea.Idea;
import com.hotmart.playground.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment implements BusinessEntity {

    private static final long serialVersionUID = 85664801415257380L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_idea")
    private Idea idea;

    @Lob
    @NotEmpty
    private String message;

    @NotNull
    private LocalDateTime createAt;
}
