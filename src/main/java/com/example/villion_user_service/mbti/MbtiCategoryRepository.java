package com.example.villion_user_service.mbti;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MbtiCategoryRepository extends CrudRepository<MbtiCategoryEntity, Long> {
}
