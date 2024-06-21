package com.example.villion_user_service.repository;

import com.example.villion_user_service.domain.entity.WishProductFolderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishProductFolderRepository extends CrudRepository<WishProductFolderEntity, Long> {

    WishProductFolderEntity findByUserId(Long userId);
}
