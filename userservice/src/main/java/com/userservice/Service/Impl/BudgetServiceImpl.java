package com.userservice.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userservice.Entities.Budget;
import com.userservice.Exception.ResourceNotFoundException;
import com.userservice.Repo.BudgetRepo;
import com.userservice.Service.BudgetService;


@Service
public class BudgetServiceImpl implements BudgetService{
    @Autowired
    private BudgetRepo budgetRepo;

    @Override
    public Budget saveBudget(Budget b) {
        return this.budgetRepo.save(b);
    }

    @Override
    public Budget getBudgetByUserId(String id) {

        Budget existingBudget = budgetRepo.findByUserId(id)
            .orElseThrow(() -> new ResourceNotFoundException("Budget with ID " + id + " not found"));
        return existingBudget;
    }

    @Override
    public Budget updateBudget(String id,double amount) {
        Budget existingBudget = budgetRepo.findByUserId(id)
            .orElseThrow(() -> new ResourceNotFoundException("Budget with ID " + id + " not found"));
        
        existingBudget.setBudget(amount);
    // ... update other fields as needed
    
    return budgetRepo.save(existingBudget);
    }
}
