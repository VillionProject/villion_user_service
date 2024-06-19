package com.example.villion_user_service.domain.eunm;

import com.example.villion_user_service.domain.entity.UserEntity;
import jakarta.persistence.ManyToMany;

import java.util.List;

public enum Category {
    NOT_SPECIFIED, //지정안함,
    HEALTH_HOBBY, // 건강/취미
    ECONOMICS_MANAGEMENT, // 경영/경제
    SCIENCE, // 과학
    TEXTBOOKS_PROFESSIONAL_BOOKS, // 대학교재/전문서적
    COMICS, // 만화
    SOCIAL_SCIENCES, // 사회과학
    FICTION_POETRY_DRAMA, // 소설/시/희곡
    EXAM_PREPARATION_CERTIFICATION, // 수험서
    CHILDREN, // 어린이
    ESSAY, // 에세이
    TRAVEL, // 여행
    HISTORY, // 역사
    ART_POP_CULTURE, // 예술/대중문화
    FOREIGN_LANGUAGE, // 외국어
    COOKING_HOUSEKEEPING, // 요리/살림
    INFANTS_TODDLERS, // 유아
    HUMANITIES, // 인문학
    SELF_HELP, // 자기계발
    RELIGION_OCCULTISM, // 종교/역학
    GOOD_PARENTING, // 좋은부모
    ADOLESCENTS, // 청소년
    COMPUTER_MOBILE // 컴퓨터/모바일

}
