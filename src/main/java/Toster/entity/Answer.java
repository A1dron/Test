package Toster.entity;

import javax.persistence.*;

@Entity
@Table
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer")
    @SequenceGenerator(name="answer", sequenceName = "seq_answer")
    private Long id;

    @Column
    private String answer;

    @Column
    private boolean isTrueAnswer;

    @ManyToOne
    @JoinColumn
    private Question question;

    public Answer(){}

    public Answer(String answer){
        this.answer = answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
