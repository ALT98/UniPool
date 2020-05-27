package com.example.unipool;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DefinitiveTripsService {

    @GET("drivers/GetCurrentTrip")
    Call<DefinitiveTripsClass> getCurrentTrip(@Query("DriverId") int driverId);
}
