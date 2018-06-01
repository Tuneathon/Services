package com.accedia.tuneathon.flutter.webservices.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class RoomController {


    @GetMapping("/test")
    public String test() {
    	return "BRAVO !!";
    }
}
