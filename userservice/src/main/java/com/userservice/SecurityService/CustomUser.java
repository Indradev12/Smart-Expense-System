package com.userservice.SecurityService;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.userservice.Entities.User;

public class CustomUser implements UserDetails{

    private String username;
    private String password;

    public CustomUser(User u){
        this.username = u.getEmail();
        this.password = u.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        System.out.println("Custom User class implements UserDetail: " + username);
        return username;
    }


    
}
