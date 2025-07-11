package com.userservice.Service;

import com.userservice.Entities.Budget;

public interface BudgetService {
    //BUDGET
    Budget saveBudget(Budget b);
    Budget updateBudget(String id,double amount);   
    Budget getBudgetByUserId(String id);
}
