package com.alertservice.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alertservice.Entities.ExpenseDocument;
import com.alertservice.ExternalService.EventCache;
import com.common.entities.Expense;

@Service
public class EventFlusher {
    
    @Autowired
    public EventCache eCache;

    @Autowired
    public ExpenseService eService;

    @Scheduled(fixedRate = 50_000)
    public void flushEvents(){
        Map<String, List<Expense>> map = eCache.flushtAndGet();

        map.forEach((userId,events) ->{
            List<ExpenseDocument> dbEvents = events.stream().map(event -> {
                ExpenseDocument e = new ExpenseDocument();
                e.setId(event.getId());
                e.setUserId(event.getUserId());
                e.setCategory(event.getCategory());
                e.setAmount(event.getAmount());
                e.setTimestamp(event.getTimestamp());
                return e;
            }).toList();
            
            eService.saveMultiple(dbEvents);

        });

        
    }
}
