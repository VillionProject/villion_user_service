package com.example.villion_user_service.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // JSON 직렬화 시에 특정 속성의 값이 null인 경우 해당 속성을 생성하지 않도록 지시
public class ResponseLogin {
    private String email;
    private String libraryName;
}
