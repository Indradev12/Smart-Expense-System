package com.expenseservice.Controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.common.entities.Expense;
import com.expenseservice.ExternalService.FeignClientService;
import com.expenseservice.Service.kafkaService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    public kafkaService kafkaService;

    @Autowired
    public FeignClientService fService;
    
    // add expense 
    @PostMapping("/create")
    public ResponseEntity<Expense> createExpense(@RequestBody Expense e) {
        String id = UUID.randomUUID().toString();
        e.setId(id);
        e.setTimestamp(LocalDateTime.now());

        String name = fService.getSecurityToken().getName();
        String userId = fService.getUserId(name);
        e.setUserId(userId);

        this.kafkaService.addExpense(e);

        return ResponseEntity.ok(e);
    }
    

    // // fetch expense
    // @GetMapping("/{userId}")
    // public ResponseEntity<List<Expense>> getExpense(@PathVariable String userId) {
    //     List<Expense> expense = this.expenseService.getExpenseByUserId(userId);
    //     return ResponseEntity.ok(expense);
    // }
    


}
