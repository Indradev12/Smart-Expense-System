package com.userservice.SecurityService;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtilService {

    // ✅ Secure 256-bit base64 encoded secret key
    private final String SECRET_KEY = "P3N0VyjzfrvKysN2Fev5zUAlqCEoG1CjHxwVZbG2pPo=";

    // public boolean validateToken(String token, UserDetails userDetails) {
    // String username = extractUsername(token);
    // return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    // }

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 mins
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // public String extractUsername(String token) {
    // return Jwts.parserBuilder()
    // .setSigningKey(getSignKey())
    // .build()
    // .parseClaimsJws(token)
    // .getBody()
    // .getSubject();
    // }

    // private boolean isTokenExpired(String token) {
    // return Jwts.parserBuilder()
    // .setSigningKey(getSignKey())
    // .build()
    // .parseClaimsJws(token)
    // .getBody()
    // .getExpiration()
    // .before(new Date());
    // }

    public Claims parseToken(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody(); // ✅ Returns the claims (userId, subject, etc.)
        } catch (JwtException e) {
            // This could be SignatureException, ExpiredJwtException, etc.
            throw new RuntimeException("Invalid or expired JWT token", e);
        }
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
