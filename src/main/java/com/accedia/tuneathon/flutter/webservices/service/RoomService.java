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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

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

    public void joinRoom(long roomId, long userId) {
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
    }
}
