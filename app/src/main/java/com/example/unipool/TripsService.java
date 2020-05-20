package com.example.unipool;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TripsService {

    @GET("passengers/SearchTrips")
    Call<Trips> searchTrips(@Query("SearchQuery") String searchQuery);

    @POST("passengers/JoinTrip")
    Call<Integer> joinTrip(@Body TripPassenger obj);

    @POST("passengers/ExitTrip")
    Call<Integer> exitTrip(@Body TripPassenger obj);

}
