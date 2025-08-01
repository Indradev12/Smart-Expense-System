package com.alertservice.Entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "expense")
public class ExpenseDocument {
    @Id
    private String id;
    private String userId;
    private String category;
    private Double amount;
    private LocalDateTime timestamp;
}
