package com.example.villion_user_service.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "mbti")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MbtiEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mbtiId;
    private String mbti;
    private String category;
}
