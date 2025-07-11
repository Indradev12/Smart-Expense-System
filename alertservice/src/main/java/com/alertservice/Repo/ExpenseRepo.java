package com.alertservice.Repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// import com.alertservice.Entities.Expense;
import com.alertservice.Entities.ExpenseDocument;

@Repository
public interface ExpenseRepo extends MongoRepository<ExpenseDocument, String> {
    public List<ExpenseDocument> findByUserId(String userId);

    Page<ExpenseDocument> findByUserIdAndCategoryAndTimestampBetween(
            String userId, String category,
            LocalDateTime start, LocalDateTime end,
            Pageable pageable);

    Page<ExpenseDocument> findByUserIdAndTimestampBetween(
            String userId,
            LocalDateTime start, LocalDateTime end,
            Pageable pageable);
}
