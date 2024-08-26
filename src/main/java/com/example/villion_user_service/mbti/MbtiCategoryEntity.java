package com.example.villion_user_service.mbti;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "mbti_category")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MbtiCategoryEntity {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String mbti;
    private String category;
}
