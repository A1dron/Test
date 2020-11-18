package Toster.services;

import Toster.entity.Answer;
import Toster.entity.Question;

import java.util.List;

public interface QuestionService {
    Question addQuestion(Question question);

    void deleteQuestion(Long id);

    List<Question> getListQuestions();

    Question getQuestionInfo(Long id);

    Question updateQuestion(Long id, String question, List<Answer> answer);
}
