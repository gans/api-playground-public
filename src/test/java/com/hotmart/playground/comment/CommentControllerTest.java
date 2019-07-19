package com.hotmart.playground.comment;

import com.hotmart.playground.IntegrationTest;
import com.hotmart.playground.category.Category;
import com.hotmart.playground.category.CategoryRepository;
import com.hotmart.playground.idea.Idea;
import com.hotmart.playground.idea.IdeaRepository;
import com.hotmart.playground.idea.IdeaStats;
import com.hotmart.playground.user.User;
import com.hotmart.playground.user.UserRepository;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.glytching.junit.extension.random.Random;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.collection.IsEmptyCollection.empty;

public class CommentControllerTest extends IntegrationTest {

    private static final String SERVICE = "/v1/ideas/{ideaId}/comments";

    private static final String IMAGE = "http://localhost:8080/image";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    private CommentRepository commentRepository;

    private Idea createIdea(User user, Category category) {
        Idea idea = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Idea.class);
        idea.setId(null);
        idea.setUser(user);
        idea.setCategory(category);
        IdeaStats stats = idea.getStats();
        stats.setId(null);
        stats.setIdea(idea);
        return idea;
    }

    @Test
    public void findAllByIdea_ReturnComments(@Random User user,
                                             @Random Category category,
                                             @Random Comment comment) {
        user.setId(null);
        userRepository.save(user);

        category.setId(null);
        category.setImage(IMAGE);
        categoryRepository.save(category);

        Idea idea = createIdea(user, category);
        ideaRepository.save(idea);

        comment.setId(null);
        comment.setUser(user);
        commentRepository.save(comment);

        newAuthenticatedRequest(user)
                .when()
                    .get(getEndpoint(getBasePath() + SERVICE), idea.getId())
                .then()
                    .log().all()
                    .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .body(matchesJsonSchemaInClasspath("json-schema/comment/comment-list.json"));
    }

    @Test
    public void findAllByIdea_ReturnEmptyList_IfIdeaNotExists(@Random User user) {
        userRepository.save(user);

        newAuthenticatedRequest(user)
                .when()
                    .get(getEndpoint(getBasePath() + SERVICE), 0)
                .then()
                    .log().all()
                    .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .body("content", empty());
    }

    @Test
    public void create_ReturnOK_IfRequestIsValid(@Random CommentCreationDto commentCreationDto,
                                                 @Random User user,
                                                 @Random Category category) {

        user.setId(null);
        userRepository.save(user);

        category.setId(null);
        category.setImage(IMAGE);
        categoryRepository.save(category);

        Idea idea = createIdea(user, category);
        ideaRepository.save(idea);

        newAuthenticatedRequest(user)
                .given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(commentCreationDto)
                .when()
                    .post(getEndpoint(getBasePath() + SERVICE), idea.getId())
                .then()
                    .log().all()
                    .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .body(matchesJsonSchemaInClasspath("json-schema/comment/comment-new.json"));
    }

    @Test
    public void create_ReturnBadRequest_IfMessageIsMissing(@Random User user,
                                                           @Random Category category) {

        user.setId(null);
        userRepository.save(user);

        category.setId(null);
        category.setImage(IMAGE);
        categoryRepository.save(category);

        Idea idea = createIdea(user, category);
        ideaRepository.save(idea);

        CommentCreationDto commentCreationDto = new CommentCreationDto();

        newAuthenticatedRequest(user)
                .given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(commentCreationDto)
                .when()
                    .post(getEndpoint(getBasePath() + SERVICE), idea.getId())
                .then()
                    .log().all()
                    .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void delete_ReturnOK_IfCommentExists(@Random User user,
                                                @Random Category category,
                                                @Random Comment comment) {

        user.setId(null);
        userRepository.save(user);

        category.setId(null);
        category.setImage(IMAGE);
        categoryRepository.save(category);

        Idea idea = createIdea(user, category);
        ideaRepository.save(idea);

        comment.setId(null);
        comment.setUser(user);
        commentRepository.save(comment);

        newAuthenticatedRequest(user)
                .when()
                    .delete(getEndpoint(getBasePath() + SERVICE + "/{commentId}"), idea.getId(), comment.getId())
                .then()
                    .log().all()
                    .assertThat()
                        .statusCode(HttpStatus.OK.value());
    }
}
