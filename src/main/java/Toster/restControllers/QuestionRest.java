package Toster.restControllers;

import Toster.entity.Answer;
import Toster.entity.Question;
import Toster.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

@Component
@RestController
@RequestMapping
public class QuestionRest {

    @Autowired
    private QuestionService questionService;

    @PostMapping(value = "/question/view")
    public List<String> getAllQuestions()
    {
        List<Question> fullQuestions = questionService.getListQuestions();
        List<String> onlyQuestions = new ArrayList<>();
        for (Question question: fullQuestions) {
            onlyQuestions.add(question.getQuestion());
        }
        return onlyQuestions;
    }

    @GetMapping(value = "/question/{id}")
    public Question getQuestionInfo(@PathVariable("id") Long id){
        return questionService.getQuestionInfo(id);
    }

    @PutMapping(value = "/question/{id}")
    public Question updateQuestion(@PathVariable("id") Long id, String question, List<Answer> answer){
        return questionService.updateQuestion(id, question, answer);
    }

    @DeleteMapping(value = "/question/{id}")
    public void deleteQuestion(@PathVariable("id")Long id){
        questionService.deleteQuestion(id);
    }

    @PostMapping(value = "/question/add")
    public Question addQuestion(Question question){
        return questionService.addQuestion(question);
    }

}
