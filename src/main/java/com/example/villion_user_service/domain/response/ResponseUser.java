package com.example.villion_user_service.domain.response;

import com.example.villion_user_service.domain.eunm.Grade;
import com.example.villion_user_service.domain.eunm.LibraryStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // JSON 직렬화 시에 특정 속성의 값이 null인 경우 해당 속성을 생성하지 않도록 지시
public class ResponseUser {
    private String email;
    private String libraryName;
    private Long phoneNumber;
    private LibraryStatus libraryStatus; // enum
    private LocalDate createdAt;
    private Grade grade; // enum
    private String profileImage;
    private int yearlyReadingTarget;
    private String familyAccount;
}
