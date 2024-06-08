package com.example.villion_user_service.service;

import com.example.villion_user_service.domain.dto.ProductDto;
import com.example.villion_user_service.domain.request.RequestAddProduct;
import com.example.villion_user_service.kafka.KafkaProducer;
import com.example.villion_user_service.kafka.TopicConfig;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final KafkaProducer kafkaProducer;

    public void addProduct(Long userId, RequestAddProduct requestAddProduct) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ProductDto productDto = mapper.map(requestAddProduct, ProductDto.class);

        // TODO product-serviced에 productEntity보내서 저장
        kafkaProducer.send(TopicConfig.addProduct, productDto);

//        productRepository.save(productEntity);
    }
}
