package com.example.unipool;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TripInfo {

    @SerializedName("tripId")
    @Expose
    private int tripId;
    @SerializedName("driverName")
    @Expose
    private String driverName;
    @SerializedName("driverDependency")
    @Expose
    private String driverDependency;
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
    @SerializedName("coordinatesLatitude")
    @Expose
    private double coordinatesLatitude;
    @SerializedName("coordinatesLongitude")
    @Expose
    private double coordinatesLongitude;

    public TripInfo(int tripId, String driverName, String driverDependency, String destination, int availableSeats, int maxCapacity, String meetingLocation, int fare, double coordinatesLatitude, double coordinatesLongitude) {
        this.tripId = tripId;
        this.driverName = driverName;
        this.driverDependency = driverDependency;
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.maxCapacity = maxCapacity;
        this.meetingLocation = meetingLocation;
        this.fare = fare;
        this.coordinatesLatitude = coordinatesLatitude;
        this.coordinatesLongitude = coordinatesLongitude;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverDependency() {
        return driverDependency;
    }

    public void setDriverDependency(String driverDependency) {
        this.driverDependency = driverDependency;
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

    public double getCoordinatesLatitude() {
        return coordinatesLatitude;
    }

    public void setCoordinatesLatitude(double coordinatesLatitude) {
        this.coordinatesLatitude = coordinatesLatitude;
    }

    public double getCoordinatesLongitude() {
        return coordinatesLongitude;
    }

    public void setCoordinatesLongitude(double coordinatesLongitude) {
        this.coordinatesLongitude = coordinatesLongitude;
    }

    @Override
    public String toString() {
        return "TripInfo{" +
                "tripId=" + tripId +
                ", driverName='" + driverName + '\'' +
                ", driverDependency='" + driverDependency + '\'' +
                ", destination='" + destination + '\'' +
                ", availableSeats=" + availableSeats +
                ", maxCapacity=" + maxCapacity +
                ", meetingLocation='" + meetingLocation + '\'' +
                ", fare=" + fare +
                ", coordinatesLatitude=" + coordinatesLatitude +
                ", coordinatesLongitude=" + coordinatesLongitude +
                '}';
    }
}
