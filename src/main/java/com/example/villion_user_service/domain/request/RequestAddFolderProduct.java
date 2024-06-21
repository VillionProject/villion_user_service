package com.example.villion_user_service.domain.request;

import lombok.Data;

@Data
public class RequestAddFolderProduct {
    private String folderName;
    private Long productId;
}
