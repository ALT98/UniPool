package com.example.unipool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InStudentTravelActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    LatLng origin, endPoint;
    String destination;
    Button btnExitTrip;
    MarkerOptions markers;
    int tripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_student_travel);

        Intent received = getIntent();

        final int travel_id = received.getIntExtra("trip_id", 0);
        tripId = travel_id;
        final int student_id = received.getIntExtra("student_id", 0);

        btnExitTrip = findViewById(R.id.btnExitTrip);
        btnExitTrip.setEnabled(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://unipool-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final TripsService service = retrofit.create(TripsService.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //loadMapData(travel_id);

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
                            Intent intent = new Intent(InStudentTravelActivity.this, MainActivity.class);
                            Toast.makeText(InStudentTravelActivity.this, "Viaje cancelado", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            btnExitTrip.setEnabled(true);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://unipool-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final TripInfoService service = retrofit.create(TripInfoService.class);

        Call<TripInfo> call = service.getTripInfo(tripId);
        call.enqueue(new Callback<TripInfo>() {
            @Override
            public void onResponse(Call<TripInfo> call, Response<TripInfo> response) {
                if(response.isSuccessful()){
                    TripInfo trip = response.body();
                    destination = trip.getDestination();
                    origin = new LatLng(trip.getCoordinatesLatitude(), trip.getCoordinatesLongitude());
                    markers = new MarkerOptions().position(origin).title("Origen");
                    googleMap.addMarker(markers);

                    Geocoder geocoder = new Geocoder(InStudentTravelActivity.this);
                    List<Address> list = new ArrayList<>();
                    try {
                        list = geocoder.getFromLocationName(destination,1);
                    }catch (IOException e){
                        Toast.makeText(InStudentTravelActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    if(list.size() > 0){
                        Address address = list.get(0);
                        endPoint = new LatLng(address.getLatitude(), address.getLongitude());
                        markers = new MarkerOptions().position(endPoint).title("Destino");
                        googleMap.addMarker(markers);

                        PolylineOptions polylineOptions = new PolylineOptions().add(origin)
                                .add(endPoint);
                        Polyline polyline = googleMap.addPolyline(polylineOptions);

                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, (float)12.5));
                    }


                }
            }

            @Override
            public void onFailure(Call<TripInfo> call, Throwable t) {
            }
        });
    }


}
