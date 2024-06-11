package com.example.villion_user_service.domain.request;

import com.example.villion_user_service.domain.eunm.ProductStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestCart {
//    #사용자
    private Long userId; // 임차하는 사람
    private Long ownerUserId; // 임대하는 사람(defalt)


//    book
    private Long productId; // bookId??
    private String author;
    private String publisher;

//    대여 내용
    private ProductStatus status; // enum
    private Long rentalQuantity;
    private LocalDate rentalPeriod; // 대여 가능 기간
    private Long rentalPrice;
    private Boolean rentable;
    private Boolean purchasable;
}
