package com.alertservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alertservice.Entities.User;
import com.alertservice.ExternalService.FeignClientService;

@Service
public class AlertService {
    
    @Autowired 
    public FeignClientService fService;

    public double getUserBudget(String userId) {
        return this.fService.getBudget(userId);
    }

    public User getUser(String userId) {
        return this.fService.getUser(userId);
    }
    
}
