package com.example.unipool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InTravelActivity extends AppCompatActivity {
    TextView lbl;
    Button btnStartTrip;
    Button btnFinishTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_travel);

        btnFinishTrip = findViewById(R.id.btnFinishTrip);
        btnStartTrip = findViewById(R.id.btnStartTrip);
        lbl = findViewById(R.id.textView);

        btnFinishTrip.setVisibility(View.INVISIBLE);

        Intent received = getIntent();
        final int trip_id = received.getIntExtra("trip_id", 0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://unipool-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final TripService service = retrofit.create(TripService.class);

        btnStartTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TripPassenger trip = new TripPassenger(trip_id);
                if(trip.getTripId() == 0){
                    return;
                }
                Call<Integer> call = service.startTrip(trip);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        lbl.setText("Viaje en curso");
                        btnStartTrip.setVisibility(View.INVISIBLE);
                        btnFinishTrip.setVisibility(View.VISIBLE);
                        Toast.makeText(InTravelActivity.this, "Ha comenzado el viaje", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        lbl.setText("Viaje en curso");
                        btnStartTrip.setVisibility(View.INVISIBLE);
                        btnFinishTrip.setVisibility(View.VISIBLE);
                        Toast.makeText(InTravelActivity.this, "Ha comenzado el viaje", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnFinishTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TripPassenger trip = new TripPassenger(trip_id);
                if(trip.getTripId() == 0){
                    return;
                }
                Call<Integer> call = service.finishTrip(trip);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        lbl.setText("Viaje finalizado");
                        btnStartTrip.setVisibility(View.INVISIBLE);
                        btnFinishTrip.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        lbl.setText("Viaje finalizado");
                        btnStartTrip.setVisibility(View.INVISIBLE);
                        btnFinishTrip.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });

    }
}
