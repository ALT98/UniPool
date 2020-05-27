package com.example.unipool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InTravelActivity extends AppCompatActivity {
    ListView listView;
    TextView lbl;
    Button btnStartTrip;
    Button btnFinishTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_travel);

        listView = findViewById(R.id.listView);
        btnFinishTrip = findViewById(R.id.btnFinishTrip);
        btnStartTrip = findViewById(R.id.btnStartTrip);
        lbl = findViewById(R.id.textView);

        btnFinishTrip.setVisibility(View.INVISIBLE);

        Intent received = getIntent();

        int studentID = received.getIntExtra("studentID", 0);
        final int trip_id = received.getIntExtra("trip_id", 0);

        fillList(listView, studentID);

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

    private void fillList(final ListView listView, int student_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://unipool-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final DefinitiveTripsService service = retrofit.create(DefinitiveTripsService.class);
        Call<DefinitiveTripsClass> call = service.getCurrentTrip(student_id);
        call.enqueue(new Callback<DefinitiveTripsClass>() {
            @Override
            public void onResponse(Call<DefinitiveTripsClass> call, Response<DefinitiveTripsClass> response) {
                if(response.isSuccessful()){
                    DefinitiveTripsClass trips = response.body();
                    List<String> nombres = new ArrayList<>();
                    for(DefinitiveStudentsClass obj : trips.getStudentsInTrip()){
                        nombres.add(obj.getStudentName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(InTravelActivity.this, android.R.layout.simple_list_item_1, nombres);
                    listView.setAdapter(adapter);
                }else{
                    Toast.makeText(InTravelActivity.this, "" + response.code() + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DefinitiveTripsClass> call, Throwable t) {
                Toast.makeText(InTravelActivity.this, "Error: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
