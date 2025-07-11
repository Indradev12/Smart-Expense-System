package com.userservice.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.userservice.DTO.UserDTO;
import com.userservice.Entities.Budget;
import com.userservice.Entities.User;
import com.userservice.Exception.ResourceNotFoundException;
import com.userservice.Helper.SecurityContextHelper;
import com.userservice.SecurityService.JwtUtilService;
import com.userservice.SecurityService.SecurityTokenService;
import com.userservice.Service.BudgetService;
import com.userservice.Service.EmailVerificationService;
import com.userservice.Service.TokensService;
import com.userservice.Service.UserService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
// @RequestMapping("/user")
public class Homeontroller {
    @Autowired
    private UserService userService;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private JwtUtilService jwtUtil;

    @Autowired
    private SecurityTokenService sTokenService;
    // fetch user
    @GetMapping("/by-id/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<String> getUserId(@PathVariable String name) {
        System.out.println("Name: " + name);
        String userId = userService.getUserId(name);
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<?> getALlUser() {
        List<User> user = this.userService.getAllUser();

        return ResponseEntity.ok(user);
    }

    // set budget
    @PostMapping("/setBudget")
    public ResponseEntity<Budget> createBudget(@RequestBody String amount,HttpServletRequest request) {
        String userId = sTokenService.getJwtToken(request).getId();
        String id = UUID.randomUUID().toString();
        Budget b = new Budget();
        b.setId(id);
        b.setUserId(userId);
        b.setBudget(Integer.parseInt(amount));
        this.budgetService.saveBudget(b);
        return ResponseEntity.ok(b);
    }

    @GetMapping("/getSecurityToken")
    public UserDTO getSecurityToken(@RequestHeader("Authorization") String token) {
        try {
            System.out.println("DEBUG: SecurityToken Method");
            System.out.println("DEBUG : Token value:" + token);
            Claims claims = jwtUtil.parseToken(token.replace
            ("Bearer ", ""));
            System.out.println("[DEBUG] Claim : "+claims.getSubject()
            );
            User user = userService.getUserByEmail(claims.getSubject());
            return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getNumber());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid or expired token.");
        }

    }

    @GetMapping("/getbudget")
    public ResponseEntity<?> getUserBudget(HttpServletRequest request) {
        System.out.println("[DEBUG] GetBUdget called");
        User u = sTokenService.getJwtToken(request);
        
        try {
            Budget b = this.budgetService.getBudgetByUserId(u.getId());
            System.out.println("[DEBUG] Budget fetched: " + b.getBudget());
            return ResponseEntity.ok().body(Map.of("budget", b.getBudget(), "username", u.getName()));
        } catch (Exception e) {
            System.out.println("[DEBUG] Budget not fecthed: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve budget", "details", e.getMessage()));
        }

    }

    // update budget
    // @PutMapping("updateBudget")
    // public ResponseEntity<String> updateBudget(@RequestParam String amount,HttpServletRequest request) {
    //     String userId = sTokenService.getJwtToken(request).getId();
    //     // TODO: process PUT request
    //     this.budgetService.updateBudget(userId, Integer.parseInt(amount));
    //     return ResponseEntity.ok("Amount Updated Successfully..");
    // }

    // delete user

}
