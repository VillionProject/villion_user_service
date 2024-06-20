package com.example.villion_user_service.repository;

import com.example.villion_user_service.domain.entity.UserEntity;
import com.example.villion_user_service.domain.entity.WishLibraryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishLibraryRepository extends CrudRepository<WishLibraryEntity, Long> {

//    WishLibraryEntity findByWishLibraryId(Long wishLibraryId);
//
//    List<WishLibraryEntity> findByUserAndWishLibraryId(UserEntity userEntity, Long wishLibraryId);

    WishLibraryEntity findByUserIdAndWishLibraryId(Long userId, Long wishLibraryId);
}
