package com.common.entities;

import java.time.LocalDateTime;

public class Expense {
    private String id;
    private String userId;
    private String category; // e.g., Food, Transport, etc.
    private Double amount;
    private LocalDateTime timestamp;

    // No-argument constructor
    public Expense() {
    }

    // All-argument constructor
    public Expense(String id, String userId, String category, Double amount, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.category = category;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
