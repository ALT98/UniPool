package com.example.unipool;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TripInfoService {

    @GET("passengers/GetTripInfo")
    Call<TripInfo> getTripInfo(@Query("TripId") int tripId);
}
