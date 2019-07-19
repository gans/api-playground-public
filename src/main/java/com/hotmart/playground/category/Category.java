package com.hotmart.playground.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hotmart.playground.common.BusinessEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category implements BusinessEntity {

    private static final long serialVersionUID = 4569734942408364448L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @Lob
    @NotEmpty
    private String description;

    @URL
    @NotEmpty
    private String image;

    public Category(Long id) {
        this.id = id;
    }
}
