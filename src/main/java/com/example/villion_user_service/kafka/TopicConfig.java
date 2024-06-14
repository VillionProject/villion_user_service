package com.example.villion_user_service.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
public class TopicConfig {
    //    public final static String customerTopic = "customer";
    public final static String addProduct = "addProduct-topic";
    public final static String addDeliveryOrder = "addDeliveryOrder-topic";
    public final static String addRentedDeliveryOrderLast1 = "addRentedDeliveryOrder-topic-last1";


    @Bean
    public NewTopic addProductTopic(){
        return TopicBuilder
                .name(addProduct)
                .replicas(1)
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic addDeliveryOrderTopic(){
        return TopicBuilder
                .name(addDeliveryOrder)
                .replicas(1)
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic addRentedDeliveryOrderLast1(){
        return TopicBuilder
                .name(addRentedDeliveryOrderLast1)
                .replicas(1)
                .partitions(1)
                .build();
    }
}