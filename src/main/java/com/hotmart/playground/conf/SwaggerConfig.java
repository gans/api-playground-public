package com.hotmart.playground.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String SECURITY_SCHEMA_NAME = "spring_oauth";

    private final ApiProperties apiProperties;

    public SwaggerConfig(ApiProperties apiProperties) {
        this.apiProperties = apiProperties;
    }

    private String authServer() {
        return apiProperties.getBaseUrl() + "/oauth";
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{
                new AuthorizationScope("read", "for read operations"),
                new AuthorizationScope("write", "for write operations")
        };
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(
                        Collections.singletonList(new SecurityReference(SECURITY_SCHEMA_NAME, scopes()))
                )
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    private SecurityScheme securityScheme() {
        GrantType tokenType = new ResourceOwnerPasswordCredentialsGrant(authServer() + "/token");

        return new OAuthBuilder().name(SECURITY_SCHEMA_NAME)
                .grantTypes(Collections.singletonList(tokenType))
                .build();
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId(apiProperties.getClientId())
                .clientSecret(apiProperties.getClientSecret())
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hotmart.playground"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()));
    }
}
