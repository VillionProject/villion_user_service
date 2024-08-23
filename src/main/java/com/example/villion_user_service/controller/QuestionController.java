package com.example.villion_user_service.controller;

import com.example.villion_user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final String[][] question = {
        {
            "상상해보세요: 주말 오후, 햇살이 가득한 창가에서 책을 읽고 있습니다. 1. 책에 완전히 몰입해 주변 세상은 잊은 듯 집중한다. 2. 가끔씩 창밖을 보며 누군가와 이 순간을 나누고 싶다는 생각을 한다.",
            "당신은 새로운 독서 모임에 초대되었습니다. 1. 모임이 끝난 후에도 혼자 책을 되새기며 생각을 정리한다. 2. 모임에서 느꼈던 감정과 의견을 다른 사람들과 더 나누고 싶어 한다.",
            "당신은 좋아하는 책이 영화로 개봉된다는 소식을 들었습니다. 1. 영화는 혼자 보면서 책과 비교하는 시간을 갖는다. 2. 친구들과 함께 영화를 보고, 각자의 생각을 나누는 것을 기대한다."
        },
        {
            "당신이 여행을 가면서 읽을 책을 골라야 합니다. 1. 여행지에 대한 역사와 사실을 다룬 책을 선택한다. 2. 여행지에서 영감을 줄 수 있는 상상력 가득한 소설을 선택한다.",
            "서점에서 무작위로 책을 집어 들었습니다. 1. 책의 목차와 세부적인 설명을 먼저 확인하며 신중하게 결정한다. 2. 책의 표지나 소개를 보고 느낌이 오는 대로 선택한다.",
            "당신이 읽던 책이 두 가지 결말을 제시했습니다. 1. 현실적인 결말이 마음에 들고, 그 가능성을 생각해본다. 2. 상상력을 자극하는 결말이 더 흥미롭고, 그 뒤에 있을 이야기를 상상해본다."
        },
        {
            "당신이 가장 감명 깊게 읽은 책이 친구에게 비판을 받았습니다. 1. 친구의 논리적인 비판을 듣고, 그 의견에 대해 논의한다. 2. 그 책이 나에게 준 감동을 설명하며 친구를 설득하려 한다.",
            "책 속 주인공이 도덕적 딜레마에 빠졌습니다. 1. 주인공이 선택한 행동의 논리적 타당성을 분석한다. 2. 주인공의 감정과 인간적인 고뇌를 깊이 이해하려고 한다.",
            "당신은 책을 읽고 난 후 리뷰를 작성하려고 합니다. 1. 책의 구조와 논리, 그리고 사실에 근거한 평가를 중점으로 쓴다. 2. 책이 불러일으킨 감정과 인간적인 면을 중심으로 표현한다."
        },
        {
            "서점에서 여러 권의 책을 샀습니다. 1. 책을 읽을 순서를 미리 정하고, 차례대로 읽어나간다. 2. 그날의 기분에 따라 어떤 책을 먼저 읽을지 즉흥적으로 결정한다.",
            "당신은 책을 읽고 나면 어떻게 보관하나요? 1. 책의 주제나 장르에 따라 깔끔하게 정리해둔다. 2. 읽은 순서대로 놓아두고, 필요할 때 찾을 수 있게 한다.",
            "당신은 독서의 날을 계획했습니다. 1. 아침부터 저녁까지 어떤 책을 몇 시간 동안 읽을지 구체적으로 계획한다. 2. 하루 종일 읽을 책들을 쌓아두고, 그때그때 마음이 가는 대로 책을 꺼내 읽는다."
        }
    };

    private final UserService userService;


    // TODO mbti 결과 보기
    @GetMapping("/mbti")
    public String getMbtiType() {
//        userService.getMbtiType();
        return null;
    }



}

