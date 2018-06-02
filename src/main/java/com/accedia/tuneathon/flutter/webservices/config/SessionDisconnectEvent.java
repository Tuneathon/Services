package com.accedia.tuneathon.flutter.webservices.config;

import org.springframework.messaging.Message;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;

@SuppressWarnings("serial")
public class SessionDisconnectEvent extends AbstractSubProtocolEvent {

    protected SessionDisconnectEvent(Object source, Message<byte[]> message) {
        super(source, message);
    }
}
