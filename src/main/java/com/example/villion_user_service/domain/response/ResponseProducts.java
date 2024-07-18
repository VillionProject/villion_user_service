package com.example.villion_user_service.domain.response;

import com.example.villion_user_service.domain.eunm.Category;
import com.example.villion_user_service.domain.eunm.ProductStatus;
import com.example.villion_user_service.domain.eunm.RentalMethod;
import com.example.villion_user_service.domain.eunm.RentalStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseProducts {
    private Long ownerUserId; // 책주인(대여받는 사람)

    private Long productId;
    private String bookName;
    @Enumerated(EnumType.STRING)
    private Category category; // enum
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus; // enum
    @Enumerated(EnumType.STRING)
    private RentalStatus rentalStatus; // 대여 상태
    private Long stockQuantity;
    private Long rentalPrice;
    @Enumerated(EnumType.STRING)
    private RentalMethod rentalMethod; // enum
    private String rentalLocation;
    private String description;
    private Boolean rentable;
    private Boolean purchasable;

    private String productImg;

}
