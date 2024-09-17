package com.example.villion_user_service.domain.request;

import lombok.Builder;
import lombok.Data;

@Data
public class LocationRequest {
    private double latitude;
    private double longitude;
}
