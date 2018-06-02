package com.accedia.tuneathon.flutter.webservices.service;

import com.accedia.tuneathon.flutter.webservices.Converter;
import com.accedia.tuneathon.flutter.webservices.Util.RoomStatus;
import com.accedia.tuneathon.flutter.webservices.dto.SocketRequest;
import com.accedia.tuneathon.flutter.webservices.dto.SocketResponse;
import com.accedia.tuneathon.flutter.webservices.entity.Room;
import com.accedia.tuneathon.flutter.webservices.entity.User;
import com.accedia.tuneathon.flutter.webservices.repository.RoomRepository;
import com.accedia.tuneathon.flutter.webservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TriviaService {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;


    public void onMessage(long roomId, long userId, SocketRequest request) {

        System.out.println("MESSAGE IS --   " + request.getAnswer() + "             ROOM ID " + roomId);

        Room room = this.roomRepository.findById(roomId).get();
        User user = userRepository.findById(userId).get();

        if (request.isJoinReq()) {
            template.convertAndSend("/topic/" + roomId, genereateMessageResponse("User " + user.getName() + " has joined the room !", room.getUserList()));
            if (room.getStatus().equals(RoomStatus.CLOSED)) {
                template.convertAndSend("/topic/" + roomId, genereateMessageResponse("All players have joined. Round 1 start !"));
                waitSeconds(3);
                template.convertAndSend("/topic/" + roomId, genereateQuestionResponse());
            }
            return;
        }

        if (room.getStatus().equals(RoomStatus.CLOSED)) {
            room.setAnsweredPeople(room.getAnsweredPeople() + 1);
            if (false) {
                user.setScore(user.getScore() + 10);
                userRepository.save(user);
            }
            template.convertAndSend("/topic/" + roomId, genereateMessageResponse("Player " + user.getName() + " answered !", room.getUserList()));
            if (room.doAllPeopleRespond()) {
                if (room.getRound() == 10) {
                    template.convertAndSend("/topic/" + roomId, genereateMessageResponse("The game end !"));
                    roomRepository.delete(room);
                } else {
                    room.setAnsweredPeople(0);
                    room.setRound(room.getRound() + 1);
                    template.convertAndSend("/topic/" + roomId, genereateMessageResponse("Round " + room.getRound() + " start !"));
                    waitSeconds(3);
                    template.convertAndSend("/topic/" + roomId, genereateQuestionResponse());
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

    private SocketResponse genereateQuestionResponse() {
        return null;
    }

}
