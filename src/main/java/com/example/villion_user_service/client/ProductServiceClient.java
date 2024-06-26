package com.example.villion_user_service.client;

import com.example.villion_user_service.domain.response.ResponseProducts;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductServiceClient {

    @GetMapping("/getProductsByLocation")
    List<ResponseProducts> getProductsByLocation();


}
