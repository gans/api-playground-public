package com.hotmart.playground.idea;

import com.hotmart.playground.security.UserDetailsDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(
        path = "${spring.data.rest.basePath}/v1/ideas",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class IdeaController {

    private final ModelMapper modelMapper;

    private final IdeaService ideaService;

    public IdeaController(ModelMapper modelMapper, IdeaService ideaService) {
        this.modelMapper = modelMapper;
        this.ideaService = ideaService;
    }

    @GetMapping
    public Page<IdeaDto> findByCategory(@RequestParam(value = "category", required = false) @NotNull final Long category,
                                        @AuthenticationPrincipal @ApiIgnore final UserDetailsDto user,
                                        final Pageable pageable) {

        return ideaService.findByCategory(category, user.toEntity(), pageable);
    }

    @PostMapping
    public Idea create(@RequestBody @Valid final IdeaCreationDto ideaDto,
                       @AuthenticationPrincipal @ApiIgnore final UserDetailsDto user) {

        Idea idea = modelMapper.map(ideaDto, Idea.class);
        idea.setId(null);
        idea.setUser(user.toEntity());
        return ideaService.save(idea);
    }

    @GetMapping("newest")
    public Page<IdeaDto> findNewestIdeas(@AuthenticationPrincipal @ApiIgnore final UserDetailsDto user) {
        return ideaService.findNewestIdeas(user.toEntity());
    }

    @GetMapping("popular")
    public Page<IdeaDto> findMostPopularIdeas(@AuthenticationPrincipal @ApiIgnore final UserDetailsDto user) {
        return ideaService.findMostPopularIdeas(user.toEntity());
    }
}
