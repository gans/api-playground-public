package com.hotmart.playground.idea;

import com.hotmart.playground.security.UserDetailsDto;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(
        path = "${spring.data.rest.basePath}/v1/ideas/{ideaId}",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class IdeaInteractionController {

    private final IdeaInteractionService ideaInteractionService;

    public IdeaInteractionController(IdeaInteractionService ideaInteractionService) {
        this.ideaInteractionService = ideaInteractionService;
    }

    @PostMapping("like")
    public void createLike(@PathVariable("ideaId") @NotNull final Long ideaId,
                           @AuthenticationPrincipal @ApiIgnore final UserDetailsDto user) {

        ideaInteractionService.like(new Idea(ideaId), user.toEntity());
    }

    @PostMapping("dislike")
    public void createDislike(@PathVariable("ideaId") @NotNull final Long ideaId,
                              @AuthenticationPrincipal @ApiIgnore final UserDetailsDto user) {

        ideaInteractionService.dislike(new Idea(ideaId), user.toEntity());
    }

    @PostMapping("join")
    public void createJoin(@PathVariable("ideaId") @NotNull final Long ideaId,
                           @AuthenticationPrincipal @ApiIgnore final UserDetailsDto user) {

        ideaInteractionService.join(new Idea(ideaId), user.toEntity());
    }

    @DeleteMapping("like")
    public void deleteLike(@PathVariable("ideaId") @NotNull final Long ideaId,
                           @AuthenticationPrincipal @ApiIgnore final UserDetailsDto user) {

        ideaInteractionService.deleteLike(new Idea(ideaId), user.toEntity());
    }

    @DeleteMapping("dislike")
    public void deleteDislike(@PathVariable("ideaId") @NotNull final Long ideaId,
                              @AuthenticationPrincipal @ApiIgnore final UserDetailsDto user) {

        ideaInteractionService.deleteDislike(new Idea(ideaId), user.toEntity());
    }

    @DeleteMapping("join")
    public void deleteJoin(@PathVariable("ideaId") @NotNull final Long ideaId,
                           @AuthenticationPrincipal @ApiIgnore final UserDetailsDto user) {

        ideaInteractionService.deleteJoin(new Idea(ideaId), user.toEntity());
    }
}
