package com.accedia.tuneathon.flutter.webservices.controller;

import com.accedia.tuneathon.flutter.webservices.dto.RoomDTO;
import com.accedia.tuneathon.flutter.webservices.dto.SocketRequest;
import com.accedia.tuneathon.flutter.webservices.dto.UserDTO;
import com.accedia.tuneathon.flutter.webservices.entity.Question;
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

    @RequestMapping(value = "/getOpened", method = RequestMethod.GET)
    public ResponseEntity<List<RoomDTO>> getOpenedRooms() {
        List<RoomDTO> allRooms = roomService.getOpenedRooms();
        return new ResponseEntity<>(allRooms, HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Long> createRoom(@RequestBody RoomDTO roomDTO, @RequestParam long userId) {
        long id = roomService.createRoom(roomDTO, userId);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public ResponseEntity<RoomDTO> joinRoom(@RequestParam long roomId, @RequestParam long userId) {
        RoomDTO dto = roomService.joinRoom(roomId, userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = "/getQuestions", method = RequestMethod.GET)
    public ResponseEntity<List<Question>> getQuestions(@RequestParam long roomId) {
        List<Question> questions = roomService.getQuestions(roomId);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @RequestMapping(value = "/sendAnswer", method = RequestMethod.POST)
    public ResponseEntity<Long> sendAnswer (@RequestParam long roomId, @RequestParam long userId, @RequestBody SocketRequest request) {
        roomService.sendAnswer(roomId, userId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/getUsersScores", method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getUsersScores(@RequestParam long roomId) {
        List<UserDTO> users = roomService.getUsersScores(roomId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
