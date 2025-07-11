package com.expenseservice.ExternalService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.expenseservice.DTO.UserDTO;

@FeignClient(name = "USER-SERVICE")
public interface FeignClientService {
    
    @GetMapping("/user/by-name/{name}")
    public String getUserId(@PathVariable String name);


    @GetMapping("user/getSecurityToken")
    public UserDTO getSecurityToken();
}
