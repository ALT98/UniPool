package com.example.unipool;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class DefinitiveTripsClass extends DefinitiveStudentsClass {

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
    @SerializedName("studentsInTrip")
    @Expose
    private DefinitiveStudentsClass[] studentsInTrip;

    public DefinitiveTripsClass(String name, int value, String studentName, int studentId, String dependency, String email, String phoneNumber, int tripId, String destination, int availableSeats, int maxCapacity, String meetingLocation, int fare, DefinitiveStudentsClass[] studentsInTrip) {
        super(name, value, studentName, studentId, dependency, email, phoneNumber);
        this.tripId = tripId;
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.maxCapacity = maxCapacity;
        this.meetingLocation = meetingLocation;
        this.fare = fare;
        this.studentsInTrip = studentsInTrip;
    }

    public DefinitiveTripsClass() {
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

    public DefinitiveStudentsClass[] getStudentsInTrip() {
        return studentsInTrip;
    }

    public void setStudentsInTrip(DefinitiveStudentsClass[] studentsInTrip) {
        this.studentsInTrip = studentsInTrip;
    }

    @Override
    public String toString() {
        return "DefinitiveTripsClass{" +
                "tripId=" + tripId +
                ", destination='" + destination + '\'' +
                ", availableSeats=" + availableSeats +
                ", maxCapacity=" + maxCapacity +
                ", meetingLocation='" + meetingLocation + '\'' +
                ", fare=" + fare +
                ", studentsInTrip=" + Arrays.toString(studentsInTrip) +
                '}';
    }
}
