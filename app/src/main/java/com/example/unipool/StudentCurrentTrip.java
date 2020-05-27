package com.example.unipool;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentCurrentTrip {

    @SerializedName("tripId")
    @Expose
    private int tripId;

    @SerializedName("destination")
    @Expose
    private String destination;

    @SerializedName("availableSeats")
    @Expose
    private int availableSeats;

    @SerializedName("maxCapacity")
    @Expose
    private int maxCapacity;

    @SerializedName("meetingLocation")
    @Expose
    private String meetingLocation;

    @SerializedName("fare")
    @Expose
    private int fare;

    public StudentCurrentTrip(int tripId, String destination, int availableSeats, int maxCapacity, String meetingLocation, int fare) {
        this.tripId = tripId;
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.maxCapacity = maxCapacity;
        this.meetingLocation = meetingLocation;
        this.fare = fare;
    }

    public StudentCurrentTrip() {
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    @Override
    public String toString() {
        return "StudentCurrentTrip{" +
                "tripId=" + tripId +
                ", destination='" + destination + '\'' +
                ", availableSeats=" + availableSeats +
                ", maxCapacity=" + maxCapacity +
                ", meetingLocation='" + meetingLocation + '\'' +
                ", fare=" + fare +
                '}';
    }

}
