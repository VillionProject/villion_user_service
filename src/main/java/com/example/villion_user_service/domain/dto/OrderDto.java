package com.example.villion_user_service.domain.dto;

import lombok.Data;

@Data
public class OrderDto{
    private Long productId;
    private Long quantity;
    private Long price;
}
