package com.example.villion_user_service.kafka;

import com.example.villion_user_service.domain.dto.ProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, ProductDto> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, ProductDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

public void send(String topic, ProductDto productDto) {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = "";
//        try {
//            jsonInString = mapper.writeValueAsString(productDto);
//        } catch (JsonProcessingException ex) {
//            ex.printStackTrace();
//        }
//
//        kafkaTemplate.send(topic, jsonInString);

        kafkaTemplate.send(topic, productDto);
        log.info("kafka Producer sent data from the Product microservice:" + productDto);


    }

}
