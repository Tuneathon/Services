package com.accedia.tuneathon.flutter.webservices.dto;

import java.util.ArrayList;
import java.util.List;

public class SocketResponse {

    private String question;

    private long questionId;

    private String message;

    private List<UserDTO> users;


    public SocketResponse() {
        this.users = new ArrayList<>();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
