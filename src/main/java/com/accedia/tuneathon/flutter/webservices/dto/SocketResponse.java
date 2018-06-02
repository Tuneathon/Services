package com.accedia.tuneathon.flutter.webservices.dto;

public class SocketResponse {

    private String question;

    private long questionId;

    private String message;


    public SocketResponse() {

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
}
