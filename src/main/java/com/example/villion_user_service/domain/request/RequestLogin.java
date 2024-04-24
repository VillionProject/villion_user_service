package com.example.villion_user_service.domain.request;

import lombok.Data;

@Data
public class RequestLogin {
    private String email;
    private String password;
}
