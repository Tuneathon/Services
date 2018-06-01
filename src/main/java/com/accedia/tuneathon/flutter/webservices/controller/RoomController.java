package com.accedia.tuneathon.flutter.webservices.controller;

import com.accedia.tuneathon.flutter.webservices.dto.RoomDTO;
import com.accedia.tuneathon.flutter.webservices.entity.Room;
import com.accedia.tuneathon.flutter.webservices.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/test")
    public String test() {
    	return "BRAVO !!";
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RoomDTO>> getOpenedRooms() {
        List<RoomDTO> allRooms = roomService.getOpenedRooms();
        return new ResponseEntity<>(allRooms, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Long> createRoom(@RequestBody RoomDTO roomDTO) {
        long id = roomService.createRoom(roomDTO);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
