package com.example.villion_user_service.domain.entity;

import com.example.villion_user_service.domain.eunm.Grade;
import com.example.villion_user_service.domain.eunm.LibraryStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 20, unique = true)
    private String email;

    @Column(nullable = false, length = 10)
    private String password;

    @Column(nullable = false, unique = true)
    private String encryptedPwd;

//    @Column(nullable = false)
    private Long phoneNumber;

//    @Column(nullable = false, length = 10)
    private String libraryName;

//    @Column(nullable = false)
    private LibraryStatus libraryStatus; // enum

    private LocalDate createdAt;

    private Grade grade; // enum

    private String profileImage;

    private int yearlyReadingTarget;

    private String familyAccount; // TODO 고민 필요.. 친구맺기 개념

//    private List<ProductEntity> registeredProducts; // 등록한 상품
//    private List<ProductEntity> purchasedProducts; // 구매한 상품
//    private List<ProductEntity> recentlyViewedProducts; // 최근에 본 상품
}
