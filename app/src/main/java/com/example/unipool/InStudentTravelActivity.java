package com.example.unipool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InStudentTravelActivity extends AppCompatActivity {

    Button btnExitTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_student_travel);

        btnExitTrip = findViewById(R.id.btnExitTrip);
        btnExitTrip.setEnabled(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://unipool-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final TripsService service = retrofit.create(TripsService.class);

        Intent received = getIntent();

        final int travel_id = received.getIntExtra("trip_id", 0);
        final int student_id = received.getIntExtra("student_id", 0);

        btnExitTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnExitTrip.setEnabled(false);
                TripPassenger trip = new TripPassenger(travel_id, student_id);
                if(trip.getStudentId() != 0 && trip.getTripId() != 0){
                    Call<Integer> createCall = service.exitTrip(trip);
                    createCall.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if(!response.isSuccessful()){
                                Toast.makeText(InStudentTravelActivity.this, "Viaje cancelado", Toast.LENGTH_SHORT).show();
                                btnExitTrip.setEnabled(true);
                            }

                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            Toast.makeText(InStudentTravelActivity.this, "Viaje cancelado", Toast.LENGTH_SHORT).show();
                            btnExitTrip.setEnabled(true);
                        }
                    });
                }
            }
        });
    }
}
