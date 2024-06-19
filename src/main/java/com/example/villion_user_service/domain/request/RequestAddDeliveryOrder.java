package com.example.villion_user_service.domain.request;

import com.example.villion_user_service.domain.dto.OrderDto;
import com.example.villion_user_service.domain.entity.ProductEntity;
import com.example.villion_user_service.domain.eunm.PaymentMethod;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RequestAddDeliveryOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long renterUserId; // 대여하는 사람

    // 배송 내역 정보
    private String userName;
    private Long phoneNumber;
    private String address;
    private String deliveryMemo;
//    private LocalDate rentalPeriod;
    private LocalDate rentalStartDate; //  대여시작일
    private LocalDate rentalEndDate; // 대여마감일
//    private Long totalRentalQuantity;
    private Long shippingCost;
//    private Long totalRentalPrice;
    private Long usedPoints;
    private List<OrderDto> orderList; // 주문 내역은 화면에서 받아서 보여주기


    // 결제 내역 정보
    private PaymentMethod paymentMethod;
}
