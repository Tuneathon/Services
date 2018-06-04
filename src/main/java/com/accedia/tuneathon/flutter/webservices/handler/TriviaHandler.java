package com.accedia.tuneathon.flutter.webservices.handler;


import com.accedia.tuneathon.flutter.webservices.Util.SocketStorage;
import com.accedia.tuneathon.flutter.webservices.dto.SocketRequest;
import com.accedia.tuneathon.flutter.webservices.service.TriviaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
public class TriviaHandler extends TextWebSocketHandler {


    @Autowired
    private TriviaService triviaService;


    private ObjectMapper mapper;

    public TriviaHandler() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        SocketStorage.getInstance().addSocket(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            SocketRequest req =  mapper.readValue(message.getPayload(), SocketRequest.class);
            triviaService.onMessage(req.getRoomId(), req.getUserId(), req);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        triviaService.handleSessionDisconect(session);

    }

}
