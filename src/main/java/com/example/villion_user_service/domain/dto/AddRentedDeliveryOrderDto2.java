package com.example.villion_user_service.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddRentedDeliveryOrderDto2 {
    private Long ownerUserId; // 책주인(대여받는 사람)(defalt)
    private Long renterUserId; // 대여하는 사람
//    private LocalDate rentalStartDate; //  대여시작일
//    private LocalDate rentalEndDate; // 대여마감일
//    private Long totalRentalQuantity;
//    private Long totalRentalPrice;

}
