package Toster.entity;

import Toster.enums.QuestionDifficulty;
import Toster.enums.QuestionType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Question {

    public Question(){}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questions")
    @SequenceGenerator(name="questions", sequenceName = "seq_questions")
    private Long id;

    @Column
    private String question;

    @Enumerated(EnumType.STRING)
    private QuestionDifficulty questionDifficulty;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Column
    private String author;



    @OneToMany(mappedBy = "question")
    private List<Answer> answers;
    public void addAnswer(Answer answer){
        answers.add(answer);
        answer.setAnswer(this.question);
    }

    @ManyToMany
    @JoinTable(name ="test_to_question",
            joinColumns = {
                    @JoinColumn(name = "questId")},
            inverseJoinColumns = {
                    @JoinColumn (name = "testId")})
    private List<Test> test;

    public Question(String question, QuestionDifficulty questionDifficulty, QuestionType type, String author, List<Answer> answers) {
        this.question = question;
        this.questionDifficulty = questionDifficulty;
        this.type = type;
        this.author = author;
        this.answers = answers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public QuestionDifficulty getDifficulty() {
        return questionDifficulty;
    }

    public void setDifficulty(QuestionDifficulty questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Test> getTest() {
        return test;
    }

    public boolean isTrueAnswer(){
        return true;
    }

    public void setTest(List<Test> test) {
        this.test = test;
    }

    public QuestionDifficulty getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(QuestionDifficulty questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

}
