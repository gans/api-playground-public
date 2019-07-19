package com.hotmart.playground.security;

import com.hotmart.playground.user.User;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Value
public class UserDetailsDto implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Collection<? extends GrantedAuthority> authorities;

    private Long id;

    private String password;

    private String username;

    public UserDetailsDto(User user) {
        this.id = user.getId();
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User toEntity() {
        return new User(id);
    }
}
