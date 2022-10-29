package com.example.hospitalmanagementsystem;

public class AppointmentModel {
    public String id;
    public String appointBy;
    public  String appointTo;
    public String appointDate;
    public String appointTime;
    public int roomNumber;
    public boolean status;

    public AppointmentModel(String id, String appointBy, String appointTo, String appointDate, String appointTime, int roomNumber, boolean status) {
        this.id = id;
        this.appointBy = appointBy;
        this.appointTo = appointTo;
        this.appointDate = appointDate;
        this.appointTime = appointTime;
        this.roomNumber = roomNumber;
        this.status = status;
    }
}
