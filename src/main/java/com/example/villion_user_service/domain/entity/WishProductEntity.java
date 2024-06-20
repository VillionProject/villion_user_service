package com.example.villion_user_service.domain.entity;

import com.example.villion_user_service.domain.eunm.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "wishProducts")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long userId; // 내 userId

    private Long productId;

}
