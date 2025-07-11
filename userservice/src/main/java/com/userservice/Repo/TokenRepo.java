package com.userservice.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.userservice.Entities.Tokens;

@Repository
public interface TokenRepo extends JpaRepository<Tokens, Long> {

    Tokens findByConfirmationToken(String token);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Tokens t WHERE t.userEmail = :email AND t.verified = true")
    boolean isUserVerified(@Param("email") String email);


    boolean existsByUserEmailAndVerifiedTrue(String email);

}
