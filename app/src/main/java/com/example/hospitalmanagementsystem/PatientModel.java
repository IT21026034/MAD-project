package com.example.hospitalmanagementsystem;

public class PatientModel {
    public String id;
    public  String name;
    public String roomNo;
    public  String ward;
    public  String allocationDate;
    public  String profileImageUrl;

    public PatientModel(String id, String name, String roomNo, String ward, String allocationDate, String profileImageUrl) {
        this.id = id;
        this.name = name;
        this.roomNo = roomNo;
        this.ward = ward;
        this.allocationDate = allocationDate;
        this.profileImageUrl = profileImageUrl;
    }
}
