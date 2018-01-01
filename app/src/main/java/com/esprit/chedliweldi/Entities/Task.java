package com.esprit.chedliweldi.Entities;

/**
 * Created by Taha on 23/12/2017.
 */

public class Task {
    private String id,name,details,time,state;

    public Task() {
    }

    public Task(String id, String name, String details, String time) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.time = time;
    }

    public Task(String name, String details, String time) {
        this.id = "";
        this.name = name;
        this.details = details;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", time='" + time + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
