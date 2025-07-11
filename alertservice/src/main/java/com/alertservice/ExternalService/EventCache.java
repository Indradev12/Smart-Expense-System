package com.alertservice.ExternalService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.common.entities.Expense;

@Component
public class EventCache {
    private final Map<String,List<Expense>> map = new ConcurrentHashMap<>();


    public void addEvent(Expense event){
        map.computeIfAbsent(event.getUserId(), k -> new ArrayList<>()).add(event);
    }

    public Map<String,List<Expense>> flushtAndGet(){
        Map<String,List<Expense>> snapshot = new HashMap<>(map);
        map.clear();
        return snapshot;
    }
}
