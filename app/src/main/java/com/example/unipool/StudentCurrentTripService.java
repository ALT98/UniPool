package com.example.unipool;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StudentCurrentTripService {

    @GET("passengers/GetCurrentTrip")
    Call<StudentCurrentTrip> getCurrentTrip(@Query("StudentId") int studentId);

}
