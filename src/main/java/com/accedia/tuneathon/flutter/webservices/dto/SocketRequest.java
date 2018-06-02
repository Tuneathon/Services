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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SocketRequest{");
        sb.append("isJoinReq=").append(isJoinReq);
        sb.append(", answer='").append(answer).append('\'');
        sb.append(", questionId=").append(questionId);
        sb.append('}');
        return sb.toString();
    }
}
