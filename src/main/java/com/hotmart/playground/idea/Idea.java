package com.hotmart.playground.idea;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hotmart.playground.common.BusinessEntity;
import com.hotmart.playground.category.Category;
import com.hotmart.playground.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Idea implements BusinessEntity {

    private static final long serialVersionUID = 790877584037125714L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    @OneToOne(mappedBy = "idea", cascade = CascadeType.ALL)
    private IdeaStats stats;

    @NotEmpty
    private String title;

    @Lob
    @NotEmpty
    private String description;

    @NotNull
    private LocalDateTime createAt;

    public Idea(Long id) {
        this.id = id;
    }
}
