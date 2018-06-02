package com.accedia.tuneathon.flutter.webservices.controller;

import com.accedia.tuneathon.flutter.webservices.dto.SocketRequest;
import com.accedia.tuneathon.flutter.webservices.service.TriviaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TriviaController {

    @Autowired
    private TriviaService triviaService;

    @MessageMapping("/room/{roomId}/user/{userId}")
    public void greet(@DestinationVariable long roomId, @DestinationVariable long userId,
                      SocketRequest message)  {

        triviaService.onMessage(roomId, userId, message);
    }
}
