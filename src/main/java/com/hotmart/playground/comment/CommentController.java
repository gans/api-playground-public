package com.hotmart.playground.comment;

import com.hotmart.playground.idea.Idea;
import com.hotmart.playground.security.UserDetailsDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(
        path = "${spring.data.rest.basePath}/v1/ideas/{ideaId}/comments",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class CommentController {

    private final ModelMapper modelMapper;

    private final CommentService commentService;

    public CommentController(ModelMapper modelMapper, CommentService commentService) {
        this.modelMapper = modelMapper;
        this.commentService = commentService;
    }

    @GetMapping
    public Page<Comment> findAllByIdea(@PathVariable("ideaId") final Long ideaId, final Pageable pageable) {
        //TODO find all comments
        return null;
    }

    @PostMapping
    public Comment create(@RequestBody @Valid final CommentCreationDto commentDto,
                          @NotNull @PathVariable("ideaId") final Long ideaId,
                          @AuthenticationPrincipal @ApiIgnore final UserDetailsDto user) {

        //TODO create comment
        return new Comment();
    }

    @DeleteMapping("{commentId}")
    public void delete(@NotNull @PathVariable("ideaId") final Long ideaId,
                       @NotNull @PathVariable("commentId") final Long commentId,
                       @AuthenticationPrincipal @ApiIgnore final UserDetailsDto user) {

        //TODO delete comment
    }
}
