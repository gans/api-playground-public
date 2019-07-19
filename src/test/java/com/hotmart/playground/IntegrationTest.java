package com.hotmart.playground;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotmart.playground.user.User;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

import static io.restassured.RestAssured.given;

@ExtendWith(RandomBeansExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {

    private static final RestAssuredConfig REST_ASSURED_CONFIG = RestAssuredConfig.config().objectMapperConfig(
            new ObjectMapperConfig().jackson2ObjectMapperFactory((t, s) -> {
                ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
                objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
                return objectMapper;
            })
    );

    @LocalServerPort
    private int port;

    @Getter
    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Getter
    @Value("${spring.data.rest.base-path}")
    private String basePath;

    @Value("${api.client-id}")
    private String clientId;

    @Value("${api.client-secret}")
    private String clientSecret;

    @Value("${api.base-url}")
    private String apiBaseUrl;

    protected String getEndpoint(final String service) {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path(contextPath)
                .path(service)
                .build().toString();
    }

    protected String getAccessToken(final String username, final String password) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);

        return Optional.ofNullable(getRestTemplate()
                .withBasicAuth(clientId, clientSecret)
                .postForEntity(getEndpoint("/oauth/token"), params, DefaultOAuth2AccessToken.class)
                .getBody())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"))
                .getValue();
    }

    protected RequestSpecification newRequest() {
        return given()
                .config(REST_ASSURED_CONFIG);
    }

    protected RequestSpecification newAuthenticatedRequest(final User user) {
        return given()
                .config(REST_ASSURED_CONFIG)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken(user.getEmail(), user.getPassword()));
    }
}
