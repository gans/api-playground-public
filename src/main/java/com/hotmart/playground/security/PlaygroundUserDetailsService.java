package com.hotmart.playground.security;

import com.hotmart.playground.user.User;
import com.hotmart.playground.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaygroundUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public PlaygroundUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByEmail(username);
        return user.map(UserDetailsDto::new)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }
}
