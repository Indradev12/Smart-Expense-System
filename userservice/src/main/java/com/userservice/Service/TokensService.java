package com.userservice.Service;

import java.util.List;

import com.userservice.Entities.Tokens;

public interface TokensService {
    //TOKENS
    Tokens findByConfirmationToken(String token);
    boolean verified(String email);
    List<Tokens> findAll();
    void deleteToken(Tokens tokens);
    Tokens saveToken(Tokens t);
    
}
