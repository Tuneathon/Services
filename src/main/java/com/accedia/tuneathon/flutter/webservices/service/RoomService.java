package com.accedia.tuneathon.flutter.webservices.service;

import com.accedia.tuneathon.flutter.webservices.Converter;
import com.accedia.tuneathon.flutter.webservices.Util.RoomStatus;
import com.accedia.tuneathon.flutter.webservices.dto.RoomDTO;
import com.accedia.tuneathon.flutter.webservices.entity.Room;
import com.accedia.tuneathon.flutter.webservices.entity.User;
import com.accedia.tuneathon.flutter.webservices.repository.RoomRepository;
import com.accedia.tuneathon.flutter.webservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;



    public List<RoomDTO> getOpenedRooms() {
        List<Room> rooms = this.roomRepository.findByStatus(RoomStatus.OPEN);
        List<RoomDTO> roomsDto = new ArrayList<>();
        for (Room room: rooms) {
            RoomDTO dto = Converter.roomEntityToDTO(room);
            roomsDto.add(dto);
        }
        return roomsDto;
    }

    public long createRoom(RoomDTO roomDTO) {
        Room room = new Room();
        room.setMaxPeople(roomDTO.getMaxPeople());
        room.setHostName(roomDTO.getHostName());
        room.setName(roomDTO.getName());
        room.setCurrentPeople(1);
        room.setStatus(RoomStatus.OPEN);
        this.roomRepository.save(room);

        User user = new User();
        user.setName(room.getHostName());
        user.setRoom(room);
        this.userRepository.save(user);

        return room.getId();
    }

}
