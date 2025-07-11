package com.alertservice.Service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.alertservice.Entities.ExpenseDocument;
import com.alertservice.ExternalService.FeignClientService;
import com.alertservice.Repo.ExpenseRepo;
import com.alertservice.Service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    public ExpenseRepo expenseRepo;

    @Override
    public ExpenseDocument creatExpense(ExpenseDocument e) {
        return this.expenseRepo.save(e);
    }

    @Override
    public List<ExpenseDocument> getExpenseByUserId(String userId) {
        return this.expenseRepo.findByUserId(userId);
    }

    @Override
    public void saveMultiple(List<ExpenseDocument> e) {
        this.expenseRepo.saveAll(e);
    }

    @Override
    public Page<ExpenseDocument> getFilteredExpenses(String userId, String category, Integer month, int page, int size,
            String[] sort) {
        System.out.println("Service class called getFiltered Expenses");
        if (month == null) {
            month = LocalDate.now().getMonthValue();
        }

        int year = LocalDate.now().getYear(); // Or take year as input if needed

        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        System.out.println("Start Date fetched: " + start);
        LocalDateTime end = start.withDayOfMonth(start.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59)
                .withSecond(59);
        System.out.println("End Date fetched: " + end);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort[1]), sort[0]));

        if (category == null || category.equalsIgnoreCase("All")) {
            return expenseRepo.findByUserIdAndTimestampBetween(userId, start, end, pageable);
        }

        return expenseRepo.findByUserIdAndCategoryAndTimestampBetween(userId, category, start, end, pageable);
    }
}
