package com.example.villion_user_service.domain.entity;

import com.example.villion_user_service.domain.eunm.Category;
import com.example.villion_user_service.domain.eunm.RentalMethod;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;


//    private Category category; // enum
//    private Long quantity;
//    private LocalDate rentalPeriod;
//    private Long rentalPrice;
//    private RentalMethod rentalMethod; // enum
//    private boolean popularity;
//    private String rentalLocation;
//    private String description;
}
