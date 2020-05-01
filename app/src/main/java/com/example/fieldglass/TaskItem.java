package com.example.fieldglass;

public class TaskItem {


    String docID;
    String Service;
    String Date;
    String Acre;
    String client;

    public TaskItem(){
        //empty constructor needed to prevent accidental deletion
    }



    public TaskItem(String service, String date , String acre) {
        Service  = service;
        Date = date;
        Acre = acre;
        client = client;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }


    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
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
