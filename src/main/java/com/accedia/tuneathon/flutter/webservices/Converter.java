package com.accedia.tuneathon.flutter.webservices;

import com.accedia.tuneathon.flutter.webservices.dto.RoomDTO;
import com.accedia.tuneathon.flutter.webservices.dto.UserDTO;
import com.accedia.tuneathon.flutter.webservices.entity.Room;
import com.accedia.tuneathon.flutter.webservices.entity.User;

public class Converter {

    public static RoomDTO roomEntityToDTO(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setCurrentPeople(room.getCurrentPeople());
        dto.setHostName(room.getHostName());
        dto.setId(room.getId());
        dto.setMaxPeople(room.getMaxPeople());
        dto.setStatus(room.getStatus());
        dto.setName(room.getName());
        for (User user: room.getUserList()) {
            dto.getUserList().add(userEntityToDTO(user));
        }
        return dto;
    }

    public static UserDTO userEntityToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        return dto;
    }
}
