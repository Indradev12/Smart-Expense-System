package com.userservice.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userservice.Entities.Budget;


@Repository
public interface BudgetRepo extends JpaRepository<Budget,String>{
    public Optional<Budget> findByUserId(String userId);
    
}
