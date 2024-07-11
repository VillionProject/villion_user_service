package com.example.villion_user_service.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RestResult<T> {
    private String status;
    private T data;

    public RestResult(String emailAlreadyInUse) {
    }
}