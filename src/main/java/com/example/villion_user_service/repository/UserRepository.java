package com.example.villion_user_service.repository;

import com.example.villion_user_service.domain.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
