package com.example.fieldglass;

public class Archive {
    private String service;
    private String acre;
    private String location;
    private String date;
    private String comment;

    public Archive(){
        //Empty constructor so firebase doesn't delete all
    }

    //Variable constructor
    public Archive(String service, String acre, String location, String date, String comment){
        this.service = service;
        this.acre = acre;
        this.location = location;
        this.date = date;
        this.comment = comment;

    }

    public String getService() {
        return service;
    }

    public String getAcre() {
        return acre;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }




}
