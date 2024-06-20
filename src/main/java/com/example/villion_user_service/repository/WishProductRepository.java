package com.example.villion_user_service.repository;

import com.example.villion_user_service.domain.entity.WishProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishProductRepository extends CrudRepository<WishProductEntity, Long> {
    WishProductEntity findByUserIdAndProductId(Long userId, Long productId);
}
