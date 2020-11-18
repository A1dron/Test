package Toster.entity;

import javax.persistence.*;

@Entity
@Table
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questions")
    @SequenceGenerator(name="questions", sequenceName = "seq_questions")
    private Long id;

    @Column
    private String answer;

    @ManyToOne
    @JoinColumn
    private Question question;

    public Answer(String answer){
        this.answer = answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
