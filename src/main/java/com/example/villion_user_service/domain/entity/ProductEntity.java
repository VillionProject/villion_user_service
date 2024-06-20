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

    //    private String productName;
//    private Category category; // enum
    private Long quantity;
//    private LocalDate rentalPeriod;
    private Long price;
//    private RentalMethod rentalMethod; // enum
//    private boolean popularity;
//    private String rentalLocation;
//    private String description;

}
