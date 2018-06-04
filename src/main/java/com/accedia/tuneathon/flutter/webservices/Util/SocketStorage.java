package com.accedia.tuneathon.flutter.webservices.Util;

import org.springframework.web.socket.WebSocketSession;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SocketStorage {

    private static SocketStorage instance;

    private Map<Long, List<WebSocketSession>> socketCache;

    private SocketStorage() {
        socketCache = new ConcurrentHashMap<>();
    }


    public static SocketStorage getInstance() {
        if (instance == null) {
            synchronized (SocketStorage.class) {
                instance = new SocketStorage();
            }
        }
        return instance;
    }

    public Long getRoomIdFromUri(URI uri) {
        String id = uri.getQuery().split("roomId=")[1].split("&")[0];
        return Long.valueOf(id);
    }

    public void addSocket(WebSocketSession socket) {
        Long id = getRoomIdFromUri(socket.getUri());
        if (socketCache.containsKey(id)) {
            List<WebSocketSession> sockets = socketCache.get(id);
            sockets.add(socket);
        } else {
            List<WebSocketSession> sockets = Collections.synchronizedList(new ArrayList<WebSocketSession>());
            sockets.add(socket);
            socketCache.put(id, sockets);
        }
    }

    public List<WebSocketSession> getSocketsForRoomId(long roomId) {
        if (socketCache.get(roomId) == null) {
            return new ArrayList<>();
        }
        return socketCache.get(roomId);
    }

    public void removeSocket(WebSocketSession socket) {
        Long id = getRoomIdFromUri(socket.getUri());
        if (socketCache.containsKey(id)) {
            List<WebSocketSession> sockets = socketCache.get(id);
            sockets.removeIf(s -> s.getId().equalsIgnoreCase(socket.getId()));
            if (sockets.isEmpty()) {
                socketCache.remove(id);
            }
        }
    }

}
