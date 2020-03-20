package com.example.fieldglass;

public class Task {
    private String Job;
    private String Date;
    private int Acre;

    public Task(){
        //empty constructor needed to prevent accidental deletion
    }

    public Task(String job, String date ,int acre) {
        this.Job  = job;
        this.Date = date;
        this.Acre = acre;
    }

    public String getJob() {
        return Job;
    }

    public String getDate() {
        return Date;
    }

    public int getAcre() {
        return Acre;
    }
}
