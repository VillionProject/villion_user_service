package com.example.villion_user_service.mbti;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final String[][] questions = {
            {
                "벌써 저녁 6시! 친목 도모를 위해 회식을 하자고 한다. 1.이제 슬슬 집에 가고 싶어서 눈치 보는 중 // 2.오늘 밤을 불태워야 한다. 아무도 집에 못 간다",
                "퇴근 후 집에 갈 때 나는? 1.혼자 조용히 가고 싶다 // 2.함께 떠들면서 가고 싶다",
                "예정이었던 금요일 회식이 취소 되었다. 1.아쉽긴 하지만.. 좋은데? 나 안 나가도 되잖아? // 2.젠장.. 만날 사람 없나? 연락해 봐야지"
            },
            {
                "어떤 결정을 내렸을 때 나는? 1.한번 내린 결정은 다시 생각하지 않는다 // 2.내가 내린 결정이 맞는지 계속 생각하며 고민한다",
                "갑작스럽게 출장을 가게 되었다. 1.출장지로 가는 동안 차 안에서 별 생각 없다 // 2.출장지로 가는 동안 차 안에서 뺑소니 당한다면? 상상해본다",
                "자율좌석제로 바뀌었다! 그런데 아무도 내 옆에 앉지 앉는다면? 1.자리 많네 개꿀 // 2.왜 안 앉지? 내가 이상한 사람처럼 보이나?"
            },
            {
                "업무 회의 중 타 부서와 의견 충돌로 싸우는 동료를 보았을 때 나는? 1.왜 저런 걸로 싸우지 오 흥미진진 // 2.왜 싸우지..? 어쩌지 말려야 하나?",
                "타 부서와 싸우던 동료의 의견이 밀렸다 보다. 동료의 기분이 안 좋아 보인다면? 1.말 걸면 안 되겠다 일단 내버려 두자 // 2.왜 기분이 안좋아 보여? 얘기해 봐~",
                "동료가 메신저를 읽씹했다면? 1.바쁜가 보다 하고 별로 신경 쓰지 않는다 // 2.내가 뭔가 실수를 한 건지 대화 내용을 다시 읽어본다"
            },
            {
                "출근 준비를 할 때 나는? 1.정해둔 시간부터 준비를 하기 때문에 항상 여유롭다  // 2.분명 빨리 준비하는데 이상하게 항상 아슬아슬하게 도착한다",
                "책상을 정리할 때 나는? 1.꺼내서 사용하지 편한 순서대로 깔끔하게 정리한다 // 2.막 놓은 것 같아도 필요한 건 바로 찾을 수 있다",
                "업무가 밀렸는데 너무 피곤하다면? 1.피곤해도 하기로 한 업무를 먼저 한 후 휴식을 취한다 // 2.휴식이 먼저 집안일은 나중에 한다"
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