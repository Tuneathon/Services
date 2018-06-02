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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SocketResponse{");
        sb.append("question='").append(question).append('\'');
        sb.append(", questionId=").append(questionId);
        sb.append(", message='").append(message).append('\'');
        sb.append(", users=").append(users);
        sb.append('}');
        return sb.toString();
    }
}
