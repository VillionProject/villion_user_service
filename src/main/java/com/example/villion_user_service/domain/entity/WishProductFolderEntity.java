package com.example.villion_user_service.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "wishProductfolders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishProductFolderEntity {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long userId;

    private String folderName;

    private String products; // 찜한 product_id 들이 배열로 들어간다
}
