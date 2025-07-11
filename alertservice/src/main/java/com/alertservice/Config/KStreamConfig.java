package com.alertservice.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.common.entities.Expense;
import com.alertservice.Entities.User;
import com.alertservice.ExternalService.EventCache;
import com.alertservice.ExternalService.JavaMailService;
import com.alertservice.Service.AlertService;

@Configuration
public class KStreamConfig {

    @Autowired
    public AlertService aService;

    @Autowired
    public EventCache eventCache;

    @Autowired
    public JavaMailService jMailService;

    @Bean
    public JsonSerde<Expense> expenseJsonSerde() {
        JsonSerde<Expense> serde = new JsonSerde<>(Expense.class);
        serde.deserializer().addTrustedPackages("*");
        return serde;
    }

    @Bean
    public KStream<String, Expense> KStream(StreamsBuilder builder) {
        System.out.println("🟢 KStream bean initialized");

        KStream<String, Expense> stream = builder.stream(
                "expense-service-events",
                Consumed.with(Serdes.String(), expenseJsonSerde()));

        stream.peek((key, event) -> System.out.println("📥 Event received: " + event));

        stream.foreach((key, event) -> {
            eventCache.addEvent(event);
            System.out.println("✅ Cached: " + event.getUserId() + ", " + event.getAmount());
        });

        KGroupedStream<String, Expense> grouped = stream.groupBy(
                (key, event) -> event.getUserId(),
                Grouped.with(Serdes.String(), expenseJsonSerde()));

        KTable<String, Double> totals = grouped.aggregate(
                () -> 0.0,
                (userId, event, agg) -> agg + event.getAmount(),
                Materialized.with(Serdes.String(), Serdes.Double()));

        totals.toStream().foreach((userId, total) -> {
            double budget = aService.getUserBudget(userId);
            System.out.println("📊 [USER: " + userId + "] Total = " + total + ", Budget = " + budget);
            if (total >= 0.8 * budget) {
                System.out.println("🚨 ALERT: Spending reached 80% for user " + userId);
                User u = aService.getUser(userId);
                jMailService.sendAlertMail(userId, u.getEmail());
            }
        });
        
        return stream;
    }

}
