package com.accedia.tuneathon.flutter.webservices.config;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BeanListener {

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        System.out.println("Client with username {} disconnected" + event.getUser());
    }
}
