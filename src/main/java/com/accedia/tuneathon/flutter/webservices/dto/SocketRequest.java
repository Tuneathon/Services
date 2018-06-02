package com.accedia.tuneathon.flutter.webservices.dto;

public class SocketRequest {

    private boolean isJoinReq;

    private String answer;

    private long questionId;


    public SocketRequest() {

    }

    public boolean isJoinReq() {
        return isJoinReq;
    }

    public void setJoinReq(boolean joinReq) {
        isJoinReq = joinReq;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }
}
