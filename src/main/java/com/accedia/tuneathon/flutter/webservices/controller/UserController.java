package com.accedia.tuneathon.flutter.webservices.controller;

import com.accedia.tuneathon.flutter.webservices.dto.UserDTO;
import com.accedia.tuneathon.flutter.webservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Long> createUser(@RequestBody UserDTO userDTO) {
        long id = userService.createUser(userDTO);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
