package com.userservice.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userservice.Entities.Tokens;
import com.userservice.Repo.TokenRepo;
import com.userservice.Service.TokensService;

@Service
public class TokensServiceImpl implements TokensService{
    @Autowired
    private TokenRepo tRepo;
    
    @Override
    public void deleteToken(Tokens tokens) {
        this.tRepo.delete(tokens);
    }

    @Override
    public Tokens saveToken(Tokens t) {
        return this.tRepo.save(t);
    }

    @Override
    public Tokens findByConfirmationToken(String token) {
        return this.tRepo.findByConfirmationToken(token);
    }

    @Override
    public List<Tokens> findAll() {
        return this.tRepo.findAll();
    }

    @Override
    public boolean verified(String email) {
        // TODO Auto-generated method stub
        return this.tRepo.isUserVerified(email);
    }
}
