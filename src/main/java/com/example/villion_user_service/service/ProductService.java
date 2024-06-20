package com.example.villion_user_service.service;

import com.example.villion_user_service.domain.dto.AddRentedDeliveryOrderDto2;
import com.example.villion_user_service.domain.dto.ProductDto;
import com.example.villion_user_service.domain.entity.ProductEntity;
import com.example.villion_user_service.domain.request.RequestAddDeliveryOrder;
import com.example.villion_user_service.domain.request.RequestAddProduct;
import com.example.villion_user_service.kafka.KafkaProducer;
import com.example.villion_user_service.kafka.DeliveryOrderKafkaProducer;
import com.example.villion_user_service.kafka.TopicConfig;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final KafkaProducer kafkaProducer;
    private final DeliveryOrderKafkaProducer orderKafkaProducer;

    public void addProduct(Long userId, RequestAddProduct requestAddProduct) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ProductDto productDto = mapper.map(requestAddProduct, ProductDto.class);

        // TODO product-service에 productEntity보내서 저장
        kafkaProducer.send(TopicConfig.addProduct, productDto);

//        productRepository.save(productEntity);
    }

    public void addDeliveryOrder(Long userId, RequestAddDeliveryOrder requestAddDeliveryOrder) {
        requestAddDeliveryOrder.setRenterUserId(userId);
        orderKafkaProducer.send(TopicConfig.addDeliveryOrder, requestAddDeliveryOrder);
//        orderKafkaProducer.send(TopicConfig.addRentedDeliveryOrderLast1, requestAddDeliveryOrder);

//        System.out.println("둘다보냄");
        // TODO 재고체크

    }



}
