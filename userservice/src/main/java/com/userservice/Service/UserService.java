package com.userservice.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.userservice.Entities.Budget;
import com.userservice.Entities.Tokens;
import com.userservice.Entities.User;
import com.userservice.Exception.ResourceNotFoundException;


// @Service
public interface UserService {

    //USER
    User saveUser(User u);
    User getUserById(String id) throws ResourceNotFoundException;
    User getUserByEmail(String email);
    boolean UserExistsByEmail(String email);
    String getUserId(String name);
    List<User> getAllUser();
    void removeUserById(String id);
    
}
