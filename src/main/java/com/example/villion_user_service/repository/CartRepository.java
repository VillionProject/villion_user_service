package com.example.villion_user_service.repository;

import com.example.villion_user_service.domain.entity.CartEntity;
import com.example.villion_user_service.domain.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<CartEntity, Long> {
    List<CartEntity> findAllByUserId(Long userId);

    void deleteByUserIdAndProductId(Long userId, Long productId);
}
