package Toster.restControllers;

import Toster.entity.Answer;
import Toster.entity.Question;
import Toster.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Component
@RestController
@RequestMapping(value = "/question")
public class QuestionRest {

    @Autowired
    private QuestionService questionService;

    @PostMapping(value = "/view")
    public List<String> getAllQuestions()
    {
        List<Question> fullQuestions = questionService.getListQuestions();
        List<String> onlyQuestions = new ArrayList<>();
        for (Question question: fullQuestions) {
            onlyQuestions.add(question.getQuestion());
        }
        return onlyQuestions;
    }

    @GetMapping(value = "/{id}")
    public Question getQuestionInfo(@PathVariable("id") Long id){
        return questionService.getQuestionInfo(id);
    }

    @PutMapping(value = "/{id}")
    public Question updateQuestion(@PathVariable("id") Long id, @RequestBody Question question){
        return questionService.updateQuestion(id, question);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteQuestion(@PathVariable("id")Long id){
        questionService.deleteQuestion(id);
    }

    @PostMapping(value = "/add")
    public Question addQuestion(Question question){
        return questionService.addQuestion(question);
    }

}
