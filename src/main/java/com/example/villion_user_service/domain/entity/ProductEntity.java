package com.example.villion_user_service.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
//@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long productId;

    private String productImage;

    private String folderName;

}
