package com.example.villion_user_service.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RestError {
    private String id;
    private String message;
}