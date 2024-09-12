package com.example.villion_user_service.mbti;

import com.example.villion_user_service.domain.eunm.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class MbtiCategoryController {
    private final MbtiCategoryService mbtiCategoryService;

    @GetMapping("/getMbtiCategory/{userId}")
    public List<Category> getMbtiCategory(@PathVariable Long userId) {
        return mbtiCategoryService.getMbtiCategory(userId);
    }

}
