package com.accedia.tuneathon.flutter.webservices.Util;

import com.accedia.tuneathon.flutter.webservices.dto.QuestionDTO;
import com.accedia.tuneathon.flutter.webservices.dto.RoomDTO;
import com.accedia.tuneathon.flutter.webservices.dto.UserDTO;
import com.accedia.tuneathon.flutter.webservices.entity.Question;
import com.accedia.tuneathon.flutter.webservices.entity.Room;
import com.accedia.tuneathon.flutter.webservices.entity.User;

public class Converter {

    public static RoomDTO roomEntityToDTO(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setCurrentPeople(room.getCurrentPeople());
        dto.setHostName(room.getHostName());
        dto.setId(room.getId());
        dto.setMaxPeople(room.getMaxPeople());
        dto.setStatus(room.getStatus());
        dto.setName(room.getName());
        for (User user: room.getUserList()) {
            dto.getUserList().add(userEntityToDTO(user));
        }
        return dto;
    }

    public static UserDTO userEntityToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setScore(user.getScore());
        return dto;
    }

    public static QuestionDTO questionEntityToDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setQuestion(question.getQuestion());
        dto.setAnswer(question.getAnswer());
        return dto;
    }
}
