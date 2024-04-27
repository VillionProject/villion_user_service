package com.example.villion_user_service.repository;

import com.example.villion_user_service.domain.dto.UserDto;
import com.example.villion_user_service.domain.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String username);
}
