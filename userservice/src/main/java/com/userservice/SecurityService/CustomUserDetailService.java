package com.userservice.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.userservice.Entities.User;
import com.userservice.Service.UserService;

@Service
public class CustomUserDetailService implements UserDetailsService{

    @Autowired 
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(username);

        System.out.println("User Detail LoadByUserName: "+ user);

        return new CustomUser(user);
    }
    
}
