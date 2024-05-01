package com.example.villion_user_service.domain.request;

import lombok.Data;

@Data
public class RequestSignup {
    private String email;
    private String password;
    private String libraryName;
}
