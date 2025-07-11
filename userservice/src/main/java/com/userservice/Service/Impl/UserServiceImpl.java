package com.userservice.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userservice.Entities.Budget;
import com.userservice.Entities.Tokens;
import com.userservice.Entities.User;
import com.userservice.Exception.ResourceNotFoundException;
import com.userservice.Repo.BudgetRepo;
import com.userservice.Repo.UserRepo;
import com.userservice.Repo.TokenRepo;
import com.userservice.Service.UserService;

import ch.qos.logback.core.subst.Token;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    public UserRepo userRepo;

    @Override
    public User saveUser(User u) {
        return this.userRepo.save(u);
    }

    @Override
    public User getUserById(String id) {
        return userRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
    }

    @Override
    public List<User> getAllUser() {
        return this.userRepo.findAll();
    }

    @Override
    public void removeUserById(String id) {
        this.userRepo.deleteById(id);
    }
    @Override
    public String getUserId(String name) {
        return this.userRepo.findByName(name)
        .orElseThrow(() -> new ResourceNotFoundException("User with name "+ name + " does not exist..."));
    }

    

    @Override
    public boolean UserExistsByEmail(String email) {
        return this.userRepo.existsByEmail(email);
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepo.findByEmail(email)
                .orElseThrow(() -> 
                    new ResourceNotFoundException("User not found")
                );
    }
    
}
