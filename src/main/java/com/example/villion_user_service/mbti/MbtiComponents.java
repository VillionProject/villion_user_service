package com.example.villion_user_service.mbti;

import java.util.ArrayList;
import java.util.List;

public class MbtiComponents {
    private List<QuestionAnswer> questions;
    public MbtiComponents() {
        this.questions = new ArrayList<>();
    }
    public void addQuestion(String question) {
        this.questions.add(new QuestionAnswer(question));
    }
    public void incrementChoiceCount(int questionIndex, int choice) {
        if (choice == 1) {
            // #5.1번
            // 1을 선택하면 QuestionAnswer클래스에 있는 increamentChoice1Count(choice1Count)에 1을 주겠다.
            // 1번 선택 1번 개수를 세는거임.

            // questions배열 중 questionIndex번째 질문을 가져와서(get) incrementChoice1Count(choice1Count변수에 1추가)실행
            questions.get(questionIndex).incrementChoice1Count(1);
        } else if (choice == 2) {
            questions.get(questionIndex).incrementChoice2Count(1);
        }
    }
    public List<QuestionAnswer> getQuestions() {
        return questions;
    }
}
