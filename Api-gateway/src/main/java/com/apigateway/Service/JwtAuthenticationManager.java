package com.apigateway.Service;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtilService jwtUtil;

    public JwtAuthenticationManager(JwtUtilService jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        if (jwtUtil.validateToken(authToken)) {
            String username = jwtUtil.extractUsername(authToken);
            Authentication auth = new UsernamePasswordAuthenticationToken(username, null, null);
            return Mono.just(auth);
        }

        return Mono.empty(); // → causes 401 Unauthorized
    }
}
