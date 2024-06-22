package com.example.villion_user_service.repository;

import com.example.villion_user_service.domain.entity.WishProductFolderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishProductFolderRepository extends CrudRepository<WishProductFolderEntity, Long> {

    WishProductFolderEntity findByUserId(Long userId);

    List<WishProductFolderEntity> findAllByUserId(Long userId);

    Optional<WishProductFolderEntity> findByUserIdAndFolderName(Long userId, String folderName);

    List<WishProductFolderEntity> findAllByUserIdAndFolderName(Long userId, String folderName);
}
