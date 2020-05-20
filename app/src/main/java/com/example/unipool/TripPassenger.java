package com.example.unipool;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TripPassenger {
    @SerializedName("TripId")
    @Expose
    private int tripId;
    @SerializedName("studentID")
    @Expose
    private int studentId;

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public TripPassenger(int tripId, int studentId) {
        this.tripId = tripId;
        this.studentId = studentId;
    }

    public TripPassenger(int tripId){
        this.tripId = tripId;
    }

    @Override
    public String toString() {
        return "TripPassenger{" +
                "tripId=" + tripId +
                ", studentId=" + studentId +
                '}';
    }
}
