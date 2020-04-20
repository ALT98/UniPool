package com.example.unipool;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TripService {
    @POST("drivers/RegisterTrip")
    Call<Integer> registerTrip(@Body Trip trip);

}
