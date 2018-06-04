package com.accedia.tuneathon.flutter.webservices.entity;

import com.accedia.tuneathon.flutter.webservices.Util.RoomStatus;

import javax.persistence.*;
import java.util.List;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column
    private RoomStatus status;

    @Column
    private Integer maxPeople;

    @Column
    private Integer currentPeople;

    @Column
    private Integer answeredPeople;

    @Column
    private Integer round;

    @Column
    private String name;

    @Column
    private String hostName;

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    @OrderBy("score DESC")
    private List<User> userList;

    public Room(){
        this.currentPeople = 0;
        this.round = 1;
        this.answeredPeople = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public Integer getCurrentPeople() {
        return currentPeople;
    }

    public void setCurrentPeople(Integer currentPeople) {
        this.currentPeople = currentPeople;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Integer getAnsweredPeople() {
        return answeredPeople;
    }

    public void setAnsweredPeople(Integer answeredPeople) {
        this.answeredPeople = answeredPeople;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public boolean doAllPeopleRespond() {
        return this.answeredPeople == this.currentPeople;
    }

}
