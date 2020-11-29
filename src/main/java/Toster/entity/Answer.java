package Toster.entity;

import javax.persistence.*;

@Entity
@Table
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer")
    @SequenceGenerator(name = "answer", sequenceName = "seq_answer")
    private Long id;

    @Column
    private String answer;

    @Column
    private boolean isTrueAnswer;

    @ManyToOne
    @JoinColumn
    private Question question;

    public Answer() {
    }

    public Answer(String answer, boolean isTrueAnswer) {

        this.answer = answer;
        this.isTrueAnswer = isTrueAnswer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public Boolean getTrueAnswer(){
        if (!isTrueAnswer) {
            return false;
        }
        return true;
    }

    public boolean isTrueAnswer() {
        return isTrueAnswer;
    }

    public void setTrueAnswer(boolean trueAnswer) {
        isTrueAnswer = trueAnswer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
