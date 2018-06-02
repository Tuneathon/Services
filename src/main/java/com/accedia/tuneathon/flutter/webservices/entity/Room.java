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
    private int maxPeople;

    @Column
    private int currentPeople;

    @Column
    private int answeredPeople;

    @Column
    private String name;

    @Column
    private String hostName;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<User> userList;

    public Room(){}

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

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public int getCurrentPeople() {
        return currentPeople;
    }

    public void setCurrentPeople(int currentPeople) {
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

    public int getAnsweredPeople() {
        return answeredPeople;
    }

    public void setAnsweredPeople(int answeredPeople) {
        this.answeredPeople = answeredPeople;
    }
}
