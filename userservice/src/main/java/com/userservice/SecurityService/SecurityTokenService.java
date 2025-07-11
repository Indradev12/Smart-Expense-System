package com.userservice.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userservice.Entities.User;
import com.userservice.Helper.SecurityContextHelper;
import com.userservice.Service.UserService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class SecurityTokenService {

    @Autowired
    private JwtUtilService jwtUtil;

    @Autowired
    private UserService userService;

    public User getJwtToken(HttpServletRequest request){
        String token = SecurityContextHelper.extractTokenFromHeader(request);
        Claims claims = jwtUtil.parseToken(token);
        return userService.getUserByEmail(claims.getSubject());
    }
}
