package com.example.villion_user_service.domain.request;

import com.example.villion_user_service.domain.eunm.Category;
import com.example.villion_user_service.domain.eunm.Grade;
import com.example.villion_user_service.domain.eunm.LibraryStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RequestUser {
    private String libraryName;

    @Enumerated(EnumType.STRING)
    private LibraryStatus libraryStatus; // enum

    private List<Category> interstCategory;

    private int yearlyReadingTarget;

    private String base_location_id;








    // TODO 프론트에서 걸러내게 하기
//    @NotBlank(message = "이메일을 입력해주세요.")
//    @Pattern(regexp="^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9" +
//            "a-zA-Z])+[.][a-zA-Z]{2,3}$", message="이메일 주소 양식을 확인해주세요.")
    //    @Email
//    private String email;

//    @NotBlank(message = "닉네임을 입력해주세요.")
//    @Pattern(regexp = "^[a-zA-Z0-9가-힣]+$", message = "닉네임은 숫자, 한글, 영어만 가능합니다.")
//    @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하여야합니다.")
//    private String libraryName;

//    @NotBlank(message = "비밀번호를 입력해주세요.")
//    @Size(min = 4, max = 12, message = "비밀번호는 4자 이상 12자 이하여야합니다.")
//    private String password;
//
//    private Long phoneNumber;
//    private LocalDate createdAt;
//    private String profileImage;
//    private int yearlyReadingTarget;
//    private String familyAccount;


//    private Long userId;
//    private String email;
//    private String libraryName;
//    private Long phoneNumber;
//    private LibraryStatus libraryStatus; // enum
//    private LocalDate createdAt;
//    private Grade grade; // enum
//    private String profileImage;
//    private int yearlyReadingTarget;

//    private String familyAccou nt;
}
