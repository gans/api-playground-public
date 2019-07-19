package com.hotmart.playground.user;

import com.hotmart.playground.IntegrationTest;
import io.github.glytching.junit.extension.random.Random;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UserControllerTest extends IntegrationTest {

    private static final String SERVICE = "/v1/users";

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findAll_ReturnUsers(@Random User user) {
        userRepository.save(user);

        newAuthenticatedRequest(user)
                .when()
                    .get(getEndpoint(getBasePath() + SERVICE))
                .then()
                    .log().all()
                    .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .body(matchesJsonSchemaInClasspath("json-schema/user/user-list.json"));
    }

    @Test
    public void signUp_ReturnAccepted_IfRequestIsValid() {
        UserCreationDto request = new UserCreationDto();
        request.setEmail("test@email.com");
        request.setPassword("P4ssw0rd!");
        request.setPasswordConfirmation(request.getPassword());

        newRequest()
                .given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                .when()
                    .post(getEndpoint(getBasePath() + SERVICE))
                .then()
                    .log().all()
                    .assertThat()
                        .statusCode(HttpStatus.ACCEPTED.value());
    }

    @Test
    public void signUp_ReturnBadRequest_IfEmailIsInvalid() {
        UserCreationDto request = new UserCreationDto();
        request.setEmail("invalid-email");
        request.setPassword("P4ssw0rd!");
        request.setPasswordConfirmation(request.getPassword());

        newRequest()
                .given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                .when()
                    .post(getEndpoint(getBasePath() + SERVICE))
                .then()
                    .log().all()
                    .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void signUp_ReturnBadRequest_IfPasswordsNotMatch() {
        UserCreationDto request = new UserCreationDto();
        request.setEmail("test@email.com");
        request.setPassword("P4ssw0rd!-Not-Match");
        request.setPasswordConfirmation("Wrong-P4ssw0rd!");

        newRequest()
                .given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                .when()
                    .post(getEndpoint(getBasePath() + SERVICE))
                .then()
                    .log().all()
                    .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
