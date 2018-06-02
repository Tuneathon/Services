package com.accedia.tuneathon.flutter.webservices.Util;

import com.accedia.tuneathon.flutter.webservices.entity.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cache {

    private static Map<Long, List<Question>> roomsQuestions = new HashMap<>();

    public static Map<Long, List<Question>> getRoomsQuestions() {
        return roomsQuestions;
    }
}
