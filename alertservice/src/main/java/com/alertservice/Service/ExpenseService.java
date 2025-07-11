package com.alertservice.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alertservice.Entities.ExpenseDocument;


public interface ExpenseService {
    //create
    public ExpenseDocument creatExpense(ExpenseDocument e);
    
    public void saveMultiple(List<ExpenseDocument> e);

    //fetch
    public List<ExpenseDocument> getExpenseByUserId(String userId);

    public Page<ExpenseDocument> getFilteredExpenses(String userId,String category, Integer month, int page, int size, String[] sort);
    
}
