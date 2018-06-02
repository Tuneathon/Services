package com.accedia.tuneathon.flutter.webservices.service;

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

import java.util.Optional;

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
        if (request.isJoinReq()) {
            Optional<User> userOpt = userRepository.findById(userId);
            SocketResponse response = new SocketResponse();
            response.setMessage("User " + userOpt.get().getName() + " has joined the room !");
            template.convertAndSend("/topic/"+roomId, response);
        }


        Room room = this.roomRepository.findById(roomId).get();


        if (room.getStatus().equals(RoomStatus.CLOSED)) {
            SocketResponse response = new SocketResponse();
            response.setQuestion("Ima li vladi visok holesterol ?");
            response.setQuestionId(1);
            template.convertAndSend("/topic/"+roomId, response);

//            template.convertAndSend("/topic/" + roomId, request.getAnswer());
//            Question question = null;
//            if (!question.isAnsweredByEveryone) {
//                template.convertAndSend("/topic/" + roomId, question);
//            }
        }

    }

}
