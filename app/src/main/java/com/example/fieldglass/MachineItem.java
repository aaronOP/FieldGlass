package com.example.fieldglass;

public class MachineItem {

    private String make;
    private String model;
    private String issue;
    private String comment;
    private String type;
    private String year;

    public String getComment() {
        return comment;
    }

    public String getType() {
        return type;
    }

    public String getYear() {
        return year;
    }

    public MachineItem(){
        //Empty constructor for firebase
    }

    public MachineItem(String make, String model, String issue) {
        this.make = make;
        this.model = model;
        this.issue = issue;

    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getIssue() {
        return issue;
    }
}
