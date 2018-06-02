package com.accedia.tuneathon.flutter.webservices.service;

import com.accedia.tuneathon.flutter.webservices.Util.Converter;
import com.accedia.tuneathon.flutter.webservices.dto.QuestionDTO;
import com.accedia.tuneathon.flutter.webservices.entity.Question;
import com.accedia.tuneathon.flutter.webservices.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<QuestionDTO> getRandomQuestions() {
        Iterable<Question> questions = this.questionRepository.getRandomQuestions();
        List<QuestionDTO> questionsList = new ArrayList<>();
        for(Question q: questions) {
            questionsList.add(Converter.questionEntityToDTO(q));
        }
        return questionsList;
    }
}
