package com.example.villion_user_service.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "wishLibraries")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishLibraryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    @ToString.Exclude
//    private UserEntity user; // 찜한 사용자


    private Long wishLibraryId; // 다른 사람의 userId

    private Long userId; // 내 userId

//    private Long libraryName;

}
