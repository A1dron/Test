package Toster.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test")
    @SequenceGenerator(name="test", sequenceName = "seq_test")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "questions")
    @ManyToMany(mappedBy = "test")
    private List<Question> questions;

    public Test(String name, List<Question> questions) {
        this.name = name;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
