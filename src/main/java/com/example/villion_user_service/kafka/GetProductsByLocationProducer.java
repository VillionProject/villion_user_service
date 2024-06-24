package com.example.villion_user_service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GetProductsByLocationProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public GetProductsByLocationProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void send(String topic, String location) {
        kafkaTemplate.send(topic, location);
        log.info("kafka Producer sent data from the Product microservice:" + location);
    }

}
