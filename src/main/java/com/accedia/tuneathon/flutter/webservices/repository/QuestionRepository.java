package com.accedia.tuneathon.flutter.webservices.repository;

import com.accedia.tuneathon.flutter.webservices.entity.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {

    @Query(value = "SELECT * FROM Question ORDER BY RAND() LIMIT 10", nativeQuery = true)
    List<Question> getRandomQuestions();
}
