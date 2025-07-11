package com.userservice.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.userservice.Entities.User;



@Repository
public interface UserRepo extends JpaRepository<User,String>{  
    @Query("SELECT u.id FROM User u WHERE u.name = :name")
    Optional<String> findByName(@Param("name") String name); 

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);


}