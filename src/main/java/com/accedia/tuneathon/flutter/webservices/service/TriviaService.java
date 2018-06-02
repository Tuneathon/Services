package com.accedia.tuneathon.flutter.webservices.service;

import com.accedia.tuneathon.flutter.webservices.Util.Cache;
import com.accedia.tuneathon.flutter.webservices.Util.RoomStatus;
import com.accedia.tuneathon.flutter.webservices.dto.SocketRequest;
import com.accedia.tuneathon.flutter.webservices.dto.SocketResponse;
import com.accedia.tuneathon.flutter.webservices.entity.Question;
import com.accedia.tuneathon.flutter.webservices.entity.Room;
import com.accedia.tuneathon.flutter.webservices.entity.User;
import com.accedia.tuneathon.flutter.webservices.repository.QuestionRepository;
import com.accedia.tuneathon.flutter.webservices.repository.RoomRepository;
import com.accedia.tuneathon.flutter.webservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TriviaService {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public void onMessage(long roomId, long userId, SocketRequest request) {

        System.out.println("roomId: " + roomId);
        System.out.println("request getQuestionId: " + request.getQuestionId());
        System.out.println("request answer: " + request.getAnswer());
        System.out.println("request isJoinReq: " + request.isJoinReq());
        if (request.isJoinReq()) {
            Optional<User> userOpt = userRepository.findById(userId);
            if (!userOpt.isPresent()) {
                throw new IllegalArgumentException("The user doesn't exist!");
            }
            SocketResponse response = new SocketResponse();
            response.setMessage("User " + userOpt.get().getName() + " has joined the room !");
            template.convertAndSend("/topic/"+roomId, response);
        }

        Optional<Room> roomOpt = this.roomRepository.findById(roomId);
        if (!roomOpt.isPresent()) {
            throw new IllegalArgumentException("The room doesn't exist!");
        }
        Room room = roomOpt.get();

        if (room.getStatus().equals(RoomStatus.CLOSED)) {
            SocketResponse response = new SocketResponse();
            Question question = Cache.getRoomsQuestions().get(room.getId()).get(room.getRound());
            response.setQuestion(question.getQuestion());
            response.setQuestionId(question.getId());
            template.convertAndSend("/topic/" + roomId, response);

//            template.convertAndSend("/topic/" + roomId, request.getAnswer());
//            Question question = null;
//            if (!question.isAnsweredByEveryone) {
//                template.convertAndSend("/topic/" + roomId, question);
//            }
        }
    }
}
