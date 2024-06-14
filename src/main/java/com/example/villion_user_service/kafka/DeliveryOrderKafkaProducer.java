package com.example.villion_user_service.kafka;

import com.example.villion_user_service.domain.request.RequestAddDeliveryOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeliveryOrderKafkaProducer {
    private final KafkaTemplate<String, RequestAddDeliveryOrder> kafkaTemplate;

    public DeliveryOrderKafkaProducer(KafkaTemplate<String, RequestAddDeliveryOrder> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, RequestAddDeliveryOrder requestAddDeliveryOrder) {
        kafkaTemplate.send(topic, requestAddDeliveryOrder);
        log.info("kafka Producer sent data from the Product microservice:" + requestAddDeliveryOrder);
    }
}
