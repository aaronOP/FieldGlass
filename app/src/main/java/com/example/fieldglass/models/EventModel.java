package com.example.fieldglass.models;

public class EventModel {

    //Declare db strings
    private String date;
    private String event;
    private String location;
    private String time;

    //Empty constructor for firestore
    private EventModel() {}

    //for me to get data
    private EventModel(String date, String event, String location, String time) {
        this.date = date;
        this.event = event;
        this.location = location;
        this.time =time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



}
