package com.alertservice.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alertservice.Entities.ExpenseDocument;
import com.alertservice.ExternalService.FeignClientService;
import com.alertservice.Service.ExpenseService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/alert")
public class AlertController {

    @Autowired
    private FeignClientService feignClientService;

    @Autowired
    private ExpenseService expenseService;

    // pagination and sorting

    // get expense user wise
    @GetMapping("/getExpense")
    public ResponseEntity<Page<ExpenseDocument>> GetUserWiseExpene(@RequestParam(required = false) String category,
            @RequestParam(required = false) Integer month,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "timestamp,desc") String[] sort,
            HttpServletRequest request) {
        
        String userId = feignClientService.getSecurityContextUser().getId();
        System.out.println("UserId fetched from getExpense :" + userId);
        return ResponseEntity.ok(expenseService.getFilteredExpenses(userId, category, month, page, size, sort));
    }

}
