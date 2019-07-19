package com.hotmart.playground.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers()
                .antMatchers("/api/**")
                .anyRequest()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/files/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .csrf().disable()
                .cors().disable()
                .headers().frameOptions().sameOrigin();
    }
}
