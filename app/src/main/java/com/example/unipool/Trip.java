package com.example.unipool;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trip{
    @SerializedName("DriverId")
    @Expose
    private int DriverId;
    @SerializedName("Destination")
    @Expose
    private String Destination;
    @SerializedName("MaxCapacity")
    @Expose
    private int MaxCapacity;
    @SerializedName("MeetingLocation")
    @Expose
    private String MeetingLocation;
    @SerializedName("Fare")
    @Expose
    private int Fare;
    @SerializedName("DepartureTime")
    @Expose
    private String DepartureTime;

    public Trip(int driverId, String destination, int maxCapacity, String meetingLocation, int fare, String departureTime) {
        DriverId = driverId;
        Destination = destination;
        MaxCapacity = maxCapacity;
        MeetingLocation = meetingLocation;
        Fare = fare;
        DepartureTime = departureTime;
    }

    public Trip(){

    }

    public int getDriverId() {
        return DriverId;
    }

    public void setDriverId(int driverId) {
        DriverId = driverId;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public int getMaxCapacity() {
        return MaxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        MaxCapacity = maxCapacity;
    }

    public String getMeetingLocation() {
        return MeetingLocation;
    }

    public void setMeetingLocation(String meetingLocation) {
        MeetingLocation = meetingLocation;
    }

    public int getFare() {
        return Fare;
    }

    public void setFare(int fare) {
        Fare = fare;
    }

    public String getDepartureTime() {
        return DepartureTime;
    }

    public void setDepartureTime(String departureTime) {
        DepartureTime = departureTime;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "DriverId=" + DriverId +
                ", Destination='" + Destination + '\'' +
                ", MaxCapacity=" + MaxCapacity +
                ", MeetingLocation='" + MeetingLocation + '\'' +
                ", Fare=" + Fare +
                ", DepartureTime='" + DepartureTime + '\'' +
                '}';
    }
}
