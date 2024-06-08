package com.example.villion_user_service.kafka;

import com.example.villion_user_service.domain.dto.ProductDto;
import com.fasterxml.jackson.databind.JsonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

//@EnableKafka
//@Configuration
//public class KafkaProducerConfig {
//    @Bean
//    public ProducerFactory<String, ProductDto> producerFactory() {
//        Map<String, Object> properties = new HashMap<>();
//        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092"); // 9092 : kafka port
//        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // KEY 풀어주기
//        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // VALUE 풀어주기
//
//        return new DefaultKafkaProducerFactory<>(properties);
//    }
//
//    @Bean //데이터 전달하는 용도의 카프카템플릿
//    public KafkaTemplate<String, ProductDto> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//}
