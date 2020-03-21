package com.example.fieldglass;

public class TaskItem {
     String Service;
     String Date;
     String Acre;

    public TaskItem(){
        //empty constructor needed to prevent accidental deletion
    }

    public TaskItem(String service, String date , String acre) {
        Service  = service;
        Date = date;
        Acre = acre;
    }

    public String getService() {

        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public String getDate() {

        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAcre() {

        return Acre;
    }

    public void setAcre(String acre) {
        Acre = acre;
    }
}
