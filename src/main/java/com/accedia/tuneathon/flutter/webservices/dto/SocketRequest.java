package com.accedia.tuneathon.flutter.webservices.dto;

public class SocketRequest {

    private boolean joinReq;

    private String answer;

    private long questionId;

    private long userId;

    private long roomId;


    public SocketRequest() {

    }

    public boolean isJoinReq() {
        return joinReq;
    }

    public void setJoinReq(boolean joinReq) {
        this.joinReq = joinReq;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SocketRequest{");
        sb.append("isJoinReq=").append(joinReq);
        sb.append(", answer='").append(answer).append('\'');
        sb.append(", questionId=").append(questionId);
        sb.append('}');
        return sb.toString();
    }
}
