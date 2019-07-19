package com.hotmart.playground.idea;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hotmart.playground.common.BusinessEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdeaStats implements BusinessEntity {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "id_idea", referencedColumnName = "id")
    private Idea idea;

    @Min(0)
    @NotNull
    private long likes;

    @Min(0)
    @NotNull
    private long dislikes;

    @Min(0)
    @NotNull
    private long joins;

    @Min(0)
    @NotNull
    private long comments;
}
