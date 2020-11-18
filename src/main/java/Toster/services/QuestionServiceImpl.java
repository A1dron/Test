package Toster.services;

import Toster.entity.Answer;
import Toster.entity.Question;
import Toster.repositoryes.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository repository;

    @Override
    public Question addQuestion(Question question) {
        repository.save(question);
        return question;
    }

    @Override
    public void deleteQuestion(Long id) {
        repository.delete(getQuestionInfo(id));
    }

    @Override
    public List<Question> getListQuestions() {
        return repository.findAll();
    }

    @Override
    public Question getQuestionInfo(Long id) {
        Optional<Question> question = repository.findById(id);
        return question.orElseThrow(() -> {
            throw new RuntimeException("Didn't find question");
        });
    }

    @Override
    public Question updateQuestion(Long id, String question, List<Answer> answer) {
        Question updateQuestion = getQuestionInfo(id);
        updateQuestion.setQuestion(question);
        updateQuestion.setAnswers(answer);
        return updateQuestion;
    }
}
