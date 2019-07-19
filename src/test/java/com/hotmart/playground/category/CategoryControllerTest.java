package com.hotmart.playground.category;

import com.hotmart.playground.IntegrationTest;
import com.hotmart.playground.user.User;
import com.hotmart.playground.user.UserRepository;
import io.github.glytching.junit.extension.random.Random;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CategoryControllerTest extends IntegrationTest {

    private static final String SERVICE = "/v1/categories";

    private static final String IMAGE_URL = "http://localhost:8080/image";

    private static final String CATEGORY_IMAGE_PATH = "/images/stormtrooper.png";

    private static final String DESCRIPTION = "description";

    private static final String NAME = "name";

    private static final String IMAGE = "image";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void findAll_ReturnCategories(@Random User user,
                                         @Random Category category) {
        user.setId(null);
        userRepository.save(user);

        category.setId(null);
        category.setImage(IMAGE_URL);
        categoryRepository.save(category);

        newAuthenticatedRequest(user)
                .when()
                    .get(getEndpoint(getBasePath() + SERVICE))
                .then()
                    .log().all()
                    .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .body(matchesJsonSchemaInClasspath("json-schema/category/category-list.json"));
    }

    @Test
    public void create_ReturnOK_IfRequestIsValid(@Random User user) {
        userRepository.save(user);

        newAuthenticatedRequest(user)
                .given()
                    .multiPart(NAME, NAME)
                    .multiPart(DESCRIPTION, DESCRIPTION)
                    .multiPart(IMAGE, new File(getClass().getResource(CATEGORY_IMAGE_PATH).getFile()))
                .when()
                    .post(getEndpoint(getBasePath() + SERVICE))
                .then()
                    .log().all()
                    .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .body(matchesJsonSchemaInClasspath("json-schema/category/category-new.json"));
    }

    @Test
    public void create_ReturnBadRequest_IfNameIsMissing(@Random User user) {
        userRepository.save(user);

        newAuthenticatedRequest(user)
                .given()
                    .multiPart(DESCRIPTION, DESCRIPTION)
                    .multiPart(IMAGE, new File(getClass().getResource(CATEGORY_IMAGE_PATH).getFile()))
                .when()
                    .post(getEndpoint(getBasePath() + SERVICE))
                .then()
                    .log().all()
                    .assertThat()
                        .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void create_ReturnBadRequest_IfDescriptionIsMissing(@Random User user) {
        userRepository.save(user);

        newAuthenticatedRequest(user)
                .given()
                    .multiPart(NAME, NAME)
                    .multiPart(IMAGE, new File(getClass().getResource(CATEGORY_IMAGE_PATH).getFile()))
                .when()
                    .post(getEndpoint(getBasePath() + SERVICE))
                .then()
                    .log().all()
                    .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void create_ReturnBadRequest_IfImageIsMissing(@Random User user) {
        userRepository.save(user);

        newAuthenticatedRequest(null)
                .given()
                    .multiPart(NAME, NAME)
                    .multiPart(DESCRIPTION, DESCRIPTION)
                .when()
                    .post(getEndpoint(getBasePath() + SERVICE))
                .then()
                    .log().all()
                    .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
