package com.example.villion_user_service.mbti;

import com.example.villion_user_service.domain.eunm.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MbtiCategoryRepository extends CrudRepository<MbtiCategoryEntity, Long> {
    List<Category> findAllByMbti(String mbti);
}
