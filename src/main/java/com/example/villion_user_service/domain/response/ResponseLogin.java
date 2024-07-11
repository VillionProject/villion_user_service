package com.example.villion_user_service.domain.response;

import com.example.villion_user_service.domain.eunm.Category;
import com.example.villion_user_service.domain.eunm.Grade;
import com.example.villion_user_service.domain.eunm.LibraryStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // JSON 직렬화 시에 특정 속성의 값이 null인 경우 해당 속성을 생성하지 않도록 지시
@Builder
public class ResponseLogin {
    private String email;
    private String libraryName;
    private Long phoneNumber;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private LibraryStatus libraryStatus; // enum

    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    private Grade grade; // enum

    private String profileImage;

    private int yearlyReadingTarget;

    private String familyAccount; // TODO 고민 필요.. 친구맺기 개념

    @Enumerated(EnumType.STRING)
    private List<Category> interestCategory; // TODO LIST로 바꿔야함?
    private String baseLocationId;

    private boolean isLogin;
}
