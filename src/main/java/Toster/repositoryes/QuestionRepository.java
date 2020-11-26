package Toster.repositoryes;

import Toster.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(nativeQuery = true, value = "SELECT *  FROM QUESTION ORDER BY random() LIMIT 1")
    Question getRandomQuestion();
}
