package com.accedia.tuneathon.flutter.webservices.config;

import com.accedia.tuneathon.flutter.webservices.handler.TriviaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketOnlyConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(triviaHandler(), "/trivia").setAllowedOrigins("*").withSockJS();
    }

    @Bean
    public WebSocketHandler triviaHandler() {
        return new TriviaHandler();
    }

}