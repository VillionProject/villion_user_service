package com.example.villion_user_service.domain.entity;

import com.example.villion_user_service.domain.dto.OrderDto;
import com.example.villion_user_service.domain.eunm.Category;
import com.example.villion_user_service.domain.eunm.Grade;
import com.example.villion_user_service.domain.eunm.LibraryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

//    @Column(nullable = false, length = 20, unique = true)
    private String email;

//    @Column(nullable = false, length = 10)
    private String password;

//    @Column(nullable = false)
    private Long phoneNumber;

//    @Column(nullable = false, length = 10)
    private String libraryName;

//    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LibraryStatus libraryStatus; // enum

    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    private Grade grade; // enum

    private String profileImage;

    private int yearlyReadingTarget;

    private String familyAccount; // TODO 고민 필요.. 친구맺기 개념

    @ElementCollection(targetClass = Category.class)
    @CollectionTable(name = "user_interest_category", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private List<Category> interestCategory;

    private String baseLocationId;
    private String baseLocation;

    private boolean isLogin = false;
//
    private String mbti;

}
