package com.hotmart.playground.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthenticationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;

    private final AuthenticationManager authenticationManager;

    private final PlaygroundUserDetailsService playgroundUserDetailsService;

    public AuthenticationServerConfig(DataSource dataSource,
                                      @Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager,
                                      PlaygroundUserDetailsService playgroundUserDetailsService) {
        this.dataSource = dataSource;
        this.authenticationManager = authenticationManager;
        this.playgroundUserDetailsService = playgroundUserDetailsService;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(playgroundUserDetailsService);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }
}
