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
    private List<Category> interestCategory; // TODO LIST로 바꿔야함?
    private String baseLocationId;

    private boolean isLogin = false;


//    @OneToMany // 객체안에 리스트
//    private List<ProductEntity> productList; // 등록한 상품 리스트
//
//    @OneToMany
//    private List<WishLibraryEntity> wishLibraryList; // 찜 도서관
//
//    @OneToMany// 객체안에 리스트
//    private List<WishProductEntity> wishProductList; // 찜 상품

//    private List<OrderDto> orderedList; // 주문 목록 TODO LIST로 바꿔야함?

//    private List<ProductEntity> registeredProducts; // 등록한 상품
//    private List<ProductEntity> purchasedProducts; // 구매한 상품
//    private List<ProductEntity> recentlyViewedProducts; // 최근에 본 상품




//    public void setInterstCategory(List<Category> interstCategory) {
//        if (isValidCategoryList(interstCategory)) {
//            this.interstCategory = interstCategory;
//        } else {
//            throw new IllegalArgumentException("Invalid interest category list");
//        }
//    }
//
//    private boolean isValidCategoryList(List<Category> categoryList) {
//        // 유효성 검사 로직을 구현하여 올바른 카테고리인지 확인합니다.
//        // 예를 들어, 모든 카테고리가 유효한지, 중복된 카테고리가 없는지 등을 검사할 수 있습니다.
//        // 이 부분은 프로젝트의 요구 사항에 따라 구현될 수 있습니다.
//        return true; // 유효성 검사가 통과되면 true 반환
//    }


}
