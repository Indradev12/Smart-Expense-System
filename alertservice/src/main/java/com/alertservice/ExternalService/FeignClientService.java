package com.alertservice.ExternalService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.alertservice.Entities.User;

@FeignClient(name = "USER-SERVICE")
public interface FeignClientService {
    @GetMapping("/user/getbudget/{userId}")
    public Double getBudget(@PathVariable String userId);

    @GetMapping("/user/by-id/{userId}")
    public User getUser(@PathVariable String userId);

    @GetMapping("/user/getSecurityToken")
    public User getSecurityContextUser();
}
