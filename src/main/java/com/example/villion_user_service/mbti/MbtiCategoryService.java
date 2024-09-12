package com.example.villion_user_service.mbti;

import com.example.villion_user_service.domain.entity.UserEntity;
import com.example.villion_user_service.domain.eunm.Category;
import com.example.villion_user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MbtiCategoryService {
    private final MbtiCategoryRepository mbtiCategoryRepository;
    private final UserRepository userRepository;

    List<Category> getMbtiCategory(Long userId) {
        UserEntity byUserId = userRepository.findByUserId(userId);
        String mbti = byUserId.getMbti();
        List<Category> allByMbti = mbtiCategoryRepository.findAllByMbti(mbti);

        return allByMbti;
    }


}
