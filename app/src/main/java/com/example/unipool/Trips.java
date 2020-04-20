package com.example.unipool;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trips {
    @SerializedName("tripId")
    @Expose
    private int tripId;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("availableSeats")
    @Expose
    private int availableSeats;
    @SerializedName("departureTime")
    @Expose
    private String departureTime;
    @SerializedName("fare")
    @Expose
    private int fare;
    @SerializedName("trips")
    @Expose
    private List<Trips> tripsList;

    public Trips(String destination, int availableSeats, String departureTime, int fare) {
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.departureTime = departureTime;
        this.fare = fare;
    }

    public List<Trips> getTripsList() {
        return tripsList;
    }

    public void setTripsList(List<Trips> tripsList) {
        this.tripsList = tripsList;
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

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    @Override
    public String toString() {
        return "Trips{" +
                "tripId=" + tripId +
                ", destination='" + destination + '\'' +
                ", availableSeats=" + availableSeats +
                ", departureTime='" + departureTime + '\'' +
                ", fare=" + fare +
                ", tripsList=" + tripsList +
                '}';
    }
}
