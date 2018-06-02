package com.accedia.tuneathon.flutter.webservices.service;

import com.accedia.tuneathon.flutter.webservices.Util.Cache;
import com.accedia.tuneathon.flutter.webservices.Util.Converter;
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

import java.util.List;
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
        System.out.println("------------------------ RECEIVE NEW MESSAGE --------------------------");
        System.out.println("ROOM ID " + roomId);
        System.out.println("USER ID " + userId);
        System.out.println("REQUEST " + request);

        Optional<Room> roomOpt = this.roomRepository.findById(roomId);
        if (!roomOpt.isPresent()) {
            throw new IllegalArgumentException("The room doesn't exist !");
        }
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("The user doesn't exist !");
        }
        Room room = roomOpt.get();
        User user = userOpt.get();
        SocketResponse response;
        if (request.isJoinReq()) {
            response = genereateMessageResponse("User " + user.getName() + " has joined the room !", room.getUserList());
            sendMessageToRoom(roomId, response);
            if (room.getStatus().equals(RoomStatus.CLOSED)) {
                response = genereateMessageResponse("All players have joined. Round 1 start !");
                sendMessageToRoom(roomId, response);
                waitSeconds(3);
                response = genereateQuestionResponse(roomId, room.getRound());
                sendMessageToRoom(roomId, response);
            }
            return;
        }

        if (room.getStatus().equals(RoomStatus.CLOSED)) {
            room.setAnsweredPeople(room.getAnsweredPeople() + 1);
            Question question = questionRepository.findById(request.getQuestionId()).get();
            if (question.isCorrect(request.getAnswer())) {
                user.setScore(user.getScore() + 10);
                userRepository.save(user);
                response = genereateMessageResponse("Player " + user.getName() + " answered correct !", room.getUserList());
                sendMessageToRoom(roomId, response);
            } else {
                response = genereateMessageResponse("Player " + user.getName() + " answered wrong !");
                sendMessageToRoom(roomId, response);
            }

            if (room.doAllPeopleRespond()) {
                if (room.getRound() == 10) {
                    response = genereateMessageResponse("The game end !");
                    sendMessageToRoom(roomId, response);
                    roomRepository.delete(room);
                } else {
                    room.setAnsweredPeople(0);
                    room.setRound(room.getRound() + 1);
                    response = genereateMessageResponse("Round " + room.getRound() + " start !");
                    sendMessageToRoom(roomId, response);
                    waitSeconds(3);
                    response = genereateQuestionResponse(roomId, room.getRound());
                    sendMessageToRoom(roomId, response);
                }
            }
            roomRepository.save(room);
        }


    }

    private void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToRoom(long roomId, SocketResponse response) {
        System.out.println("SEND RESPONSE " + response);
        template.convertAndSend("/topic/" + roomId, response);

    }

    private SocketResponse genereateMessageResponse(String message, List<User> users) {
        SocketResponse response = genereateMessageResponse(message);
        for (User user: users) {
            response.getUsers().add(Converter.userEntityToDTO(user));
        }
        return response;
    }

    private SocketResponse genereateMessageResponse(String message) {
        SocketResponse response = new SocketResponse();
        response.setMessage(message);
        return response;
    }

    private SocketResponse genereateQuestionResponse(long roomId, int round) {
        SocketResponse response = new SocketResponse();
        Question question = Cache.getRoomsQuestions().get(roomId).get(round);
        response.setQuestion(question.getQuestion());
        response.setQuestionId(question.getId());
        return response;
    }
}
