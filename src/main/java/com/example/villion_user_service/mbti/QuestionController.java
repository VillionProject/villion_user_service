package com.example.villion_user_service.mbti;

import com.example.villion_user_service.domain.entity.UserEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final String[][] questions = {
            {
                    "주말 오후, 햇살이 가득한 창가에서 책을 읽고 있습니다. 1.책에 완전히 몰입해 주변 세상은 잊은 듯 집중한다. // 2.가끔씩 창밖을 보며 누군가와 이 순간을 나누고 싶다는 생각을 한다.",
                    "당신은 새로운 독서 모임에 초대되었습니다. 1.모임이 끝난 후에도 혼자 책을 되새기며 생각을 정리한다. // 2.모임에서 느꼈던 감정과 의견을 다른 사람들과 더 나누고 싶어 한다.",
                    "당신은 좋아하는 책이 영화로 개봉된다는 소식을 들었습니다. 1.영화는 혼자 보면서 책과 비교하는 시간을 갖는다. // 2.친구들과 함께 영화를 보고, 각자의 생각을 나누는 것을 기대한다."
            },
            {
                    "당신이 여행을 가면서 읽을 책을 골라야 합니다. 1.여행지에 대한 역사와 사실을 다룬 책을 선택한다. // 2.여행지에서 영감을 줄 수 있는 상상력 가득한 소설을 선택한다.",
                    "서점에서 무작위로 책을 집어 들었습니다. 1.책의 목차와 세부적인 설명을 먼저 확인하며 신중하게 결정한다. // 2.책의 표지나 소개를 보고 느낌이 오는 대로 선택한다.",
                    "당신이 읽던 책이 두 가지 결말을 제시했습니다. 1.현실적인 결말이 마음에 들고, 그 가능성을 생각해본다. // 2.상상력을 자극하는 결말이 더 흥미롭고, 그 뒤에 있을 이야기를 상상해본다."
            },
            {
                    "당신이 가장 감명 깊게 읽은 책이 친구에게 비판을 받았습니다. 1.친구의 논리적인 비판을 듣고, 그 의견에 대해 논의한다. // 2.그 책이 나에게 준 감동을 설명하며 친구를 설득하려 한다.",
                    "책 속 주인공이 도덕적 딜레마에 빠졌습니다. 1.주인공이 선택한 행동의 논리적 타당성을 분석한다. // 2.주인공의 감정과 인간적인 고뇌를 깊이 이해하려고 한다.",
                    "당신은 책을 읽고 난 후 리뷰를 작성하려고 합니다. 1.책의 구조와 논리, 그리고 사실에 근거한 평가를 중점으로 쓴다. // 2.책이 불러일으킨 감정과 인간적인 면을 중심으로 표현한다."
            },
            {
                    "서점에서 여러 권의 책을 샀습니다. 1.책을 읽을 순서를 미리 정하고, 차례대로 읽어나간다. // 2.그날의 기분에 따라 어떤 책을 먼저 읽을지 즉흥적으로 결정한다.",
                    "당신은 책을 읽고 나면 어떻게 보관하나요? 1.책의 주제나 장르에 따라 깔끔하게 정리해둔다. // 2.읽은 순서대로 놓아두고, 필요할 때 찾을 수 있게 한다.",
                    "당신은 독서의 날을 계획했습니다. 1.아침부터 저녁까지 어떤 책을 몇 시간 동안 읽을지 구체적으로 계획한다. // 2.하루 종일 읽을 책들을 쌓아두고, 그때그때 마음이 가는 대로 책을 꺼내 읽는다."
            }

    };

    // #1번 questions을 넣을 배열 생성
    // TODO 왜 빈 배열에 저장하지? 이미 배열에 저장되어 있는데?
    private CategoryQuestions[] categories;


    public QuestionController() {
        // #2번 class 가 생성 되면서 categories 에 카테고리와 질문 insert
        // categories 배열길이 4로 생성(카테고리가 4개니까)
        categories = new CategoryQuestions[4];
        for (int i = 0; i < categories.length; i++) {
            // #3번 카테고리 빈 배열 4개 insert..
            categories[i] = new CategoryQuestions();

            // #4번 카테고리 마다 질문 insert..
            for (String question : questions[i]) {
                categories[i].addQuestion(question);
            }
        }
    }

    // user select method..
    @PostMapping("/{category}/{questionIndex}/{choice}")
    public void voteForChoice(@PathVariable int category, @PathVariable int questionIndex, @PathVariable int choice) {
        // 유저가 질문의 답을 선택하면 해당 카테고리의 질문의 답을 1로 바꿔버림..
        // #5번 CategoryQuestions클래스에 가보자..
        categories[category].incrementChoiceCount(questionIndex, choice);
    }

    // mbti 결과 보기
    @GetMapping("/results")
    public QuestionResponse getPersonalityType() {
        // StringBuilder : 문자열을 조작하고 관리하는 클래스
        StringBuilder personalityType = new StringBuilder();

        // 카테고리의 배열을 순회함..
        for (CategoryQuestions category : categories) {
            // 순회하면서 각각의 카테고리의 질문들에 대한 답이 좌항이 더 많은지 우항이 많은지 count..
            int choice1Count = category.getQuestions().stream().mapToInt(QuestionAnswer::getChoice1Count).sum();
            int choice2Count = category.getQuestions().stream().mapToInt(QuestionAnswer::getChoice2Count).sum();

            // 좌항이 많다면 .. ISTJ  = 1번이 많고 질문이 같다면
            // 우항이 많다면 .. 반대  = 그렇지 않으면
            if (choice1Count > choice2Count) {
                personalityType.append(getPersonalityCode(category));
            } else {
                personalityType.append(getOppositePersonalityCode(category));
            }
        }

        // 질문과 유저의 mbti type return...
        QuestionResponse questionResponse = QuestionResponse.builder().questions(categories).type(personalityType.toString()).build();

        return questionResponse;
    }

    @PostMapping("/clear")
    public CategoryQuestions[] categoryClear() {
        initializeCategories();
        return categories;
    }
    private void initializeCategories() {
        categories = new CategoryQuestions[4];
        for (int i = 0; i < categories.length; i++) {
            categories[i] = new CategoryQuestions();
            for (String question : questions[i]) {
                categories[i].addQuestion(question);
            }
        }
    }

    private String getPersonalityCode(CategoryQuestions category) {
        if (category.equals(categories[0])) {
            return "I";
        } else if (category.equals(categories[1])) {
            return "S";
        } else if (category.equals(categories[2])) {
            return "T";
        } else if (category.equals(categories[3])) {
            return "J";
        }
        return "";
    }

    private String getOppositePersonalityCode(CategoryQuestions category) {
        if (category.equals(categories[0])) {
            return "E";
        } else if (category.equals(categories[1])) {
            return "N";
        } else if (category.equals(categories[2])) {
            return "F";
        } else if (category.equals(categories[3])) {
            return "P";
        }
        return "";
    }




}