package com.userservice.Helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.servlet.http.HttpServletRequest;

public class SecurityContextHelper {
    public void authName() {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                // Do something with the username
            } else {
                String username = principal.toString(); // For basic authentication cases
            }
        }
    }

    public static String extractTokenFromHeader(HttpServletRequest request) {
        System.out.println("[DEBUG]: ExtractToken method called.");
        final String authHeader = request.getHeader("Authorization");
        System.out.println("[DEBUG] : Authentication: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // strip "Bearer "
        } else {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
    }

}
