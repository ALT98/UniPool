package com.example.unipool;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StudentService {
    @GET("users/Login")
    Call<Student> Login(@Query("Email") String email, @Query("Password") String password);

    @POST("users/RegisterStudent")
    Call<Integer> create(@Body Student student);

}