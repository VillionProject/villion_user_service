package com.example.villion_user_service.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
public class TopicConfig {
    //    public final static String customerTopic = "customer";
    public final static String addProduct = "addProduct-topic";


    @Bean
    public NewTopic ownerTopic(){
        return TopicBuilder
                .name(addProduct)
                .replicas(1)
                .partitions(1)
                .build();
    }
}