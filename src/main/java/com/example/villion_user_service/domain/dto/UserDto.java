package com.example.villion_user_service.domain.dto;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String password;
    private String libraryName;

}
