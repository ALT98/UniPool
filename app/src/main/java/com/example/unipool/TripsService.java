package com.example.unipool;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TripsService {

    @GET("passengers/SearchTrips")
    Call<Trips> searchTrips(@Query("SearchQuery") String searchQuery);

}
