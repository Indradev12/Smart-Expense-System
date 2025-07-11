package com.expenseservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.common.entities.Expense;

@Service
public class kafkaService {

    @Autowired
    private KafkaTemplate<String,Expense> template;

    public void addExpense(Expense req){
        Expense event = new Expense(
            req.getId(),
            req.getUserId(),
            req.getCategory(),
            req.getAmount(),
            req.getTimestamp()
        );

        this.template.send("expense-service-events", event.getUserId() + "|" + event.getCategory(), event);
    }
    
    
    
}
