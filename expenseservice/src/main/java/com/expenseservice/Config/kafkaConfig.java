package com.expenseservice.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class kafkaConfig {
    
    @Bean
    public NewTopic topic(){
        return TopicBuilder
                    .name("expense-service-events")
                    .partitions(0)
                    .replicas(0)
                    .build();
    }
}
