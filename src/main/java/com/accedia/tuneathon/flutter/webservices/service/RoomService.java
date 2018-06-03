package com.accedia.tuneathon.flutter.webservices.service;

import com.accedia.tuneathon.flutter.webservices.Util.Cache;
import com.accedia.tuneathon.flutter.webservices.Util.Converter;
import com.accedia.tuneathon.flutter.webservices.Util.RoomStatus;
import com.accedia.tuneathon.flutter.webservices.dto.RoomDTO;
import com.accedia.tuneathon.flutter.webservices.entity.Question;
import com.accedia.tuneathon.flutter.webservices.entity.Room;
import com.accedia.tuneathon.flutter.webservices.entity.User;
import com.accedia.tuneathon.flutter.webservices.repository.QuestionRepository;
import com.accedia.tuneathon.flutter.webservices.repository.RoomRepository;
import com.accedia.tuneathon.flutter.webservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private Map<Long, Room> roomCache = new HashMap<>();

    public List<RoomDTO> getOpenedRooms() {
        List<Room> rooms = this.roomRepository.findByStatus(RoomStatus.OPEN);
        List<RoomDTO> roomsDto = new ArrayList<>();
        for (Room room: rooms) {
            RoomDTO dto = Converter.roomEntityToDTO(room);
            roomsDto.add(dto);
        }
        return roomsDto;
    }

    public long createRoom(RoomDTO roomDTO, long userId) {
        Room room = new Room();
        Optional<User> userOpt = this.userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("The user doesn't exist!");
        }
        User user = userOpt.get();
        room.setHostName(user.getName());
        room.setMaxPeople(roomDTO.getMaxPeople());
        room.setName(roomDTO.getName());
        room.setCurrentPeople(1);
        room.setStatus(RoomStatus.OPEN);
        this.roomRepository.save(room);

        user.setRoom(room);
        this.userRepository.save(user);

        Cache.getRoomsQuestions().put(room.getId(), this.questionRepository.getRandomQuestions());

        return room.getId();
    }

    public RoomDTO joinRoom(long roomId, long userId) {
        Optional<Room> roomOpt = this.roomRepository.findById(roomId);
        if (!roomOpt.isPresent()) {
            throw new IllegalArgumentException("The room doesn't exist or is deleted by the host!");
        }
        Room room = roomOpt.get();
        if (room.getStatus().equals(RoomStatus.CLOSED)) {
            throw new IllegalArgumentException("The room is full!");
        }
        Optional<User> userOpt = this.userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("The user doesn't exist!");
        }
        User user = userOpt.get();
        user.setRoom(room);
        room.setCurrentPeople(room.getCurrentPeople() + 1);
        if (room.getMaxPeople() == room.getCurrentPeople()) {
            room.setStatus(RoomStatus.CLOSED);
        }
        this.roomRepository.save(room);
        this.userRepository.save(user);

        roomCache.put(roomId, room);

        return Converter.roomEntityToDTO(room);
    }

    public List<Question> getQuestions(long roomId) {
        Optional<Room> roomOpt;
        Room room;
        do {
            if (roomCache.get(roomId) != null) {
                room = roomCache.get(roomId);
            } else {
                roomOpt = this.roomRepository.findById(roomId);
                if (!roomOpt.isPresent()) {
                    throw new IllegalArgumentException("The room doesn't exist or is closed by the host!");
                }
                room = roomOpt.get();
                roomCache.put(roomId, room);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while(!room.getStatus().equals(RoomStatus.CLOSED));
        roomCache.remove(roomId);
        return Cache.getRoomsQuestions().get(roomId);
    }
}
