package com.example.unipool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeDriverActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    GoogleMap map;
    GoogleApiClient googleApiClient;

    List<MarkerOptions> listMarkers = new ArrayList<>();
    LatLng origin = null;
    LatLng destination = null;
    Polyline polyline = null;
    PolylineOptions polylineOptions = null;
    private static final int Request_User_Location_Code = 99;
    private TextInputLayout textTravelDestination;
    private TextInputLayout textMaxCapacity;
    private TextInputLayout textMeetingPoint;
    private TextInputLayout textFare;
    private TextInputLayout textDepartureTime;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_driver);
        this.setTitle(R.string.startTrip);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkUserLocationPermission();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        textTravelDestination = findViewById(R.id.textTravelDestination);
        textMaxCapacity = findViewById(R.id.textMaxCapacity);
        textMeetingPoint = findViewById(R.id.textMeetingPoint);
        textFare = findViewById(R.id.textFee);
        textDepartureTime = findViewById(R.id.textDepartureTime);

        Intent intent = getIntent();
        final String studentID = intent.getStringExtra("studentID");
        final String studentName = intent.getStringExtra("studentName");

        button = findViewById(R.id.button);

        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);
        textDepartureTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(HomeDriverActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hour = "";
                        if(hourOfDay >= 0 && hourOfDay <= 9){
                            hour += "0" + hourOfDay;
                        }else{
                            hour += hourOfDay;
                        }

                        if(minute >=0 && minute <= 9){
                            hour += ":0" + minute;
                        }else{
                            hour+= ":" + minute;
                        }

                        textDepartureTime.getEditText().setText(hour);
                        textDepartureTime.setEnabled(false);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String travelDestination = textTravelDestination.getEditText().getText().toString();
                String maxCapacity = textMaxCapacity.getEditText().getText().toString();
                String meetingPoint = textMeetingPoint.getEditText().getText().toString();
                String fare = textFare.getEditText().getText().toString();
                String departureTime = textDepartureTime.getEditText().getText().toString();
                String originLatitude = "" + origin.latitude;
                String originLongitude = "" + origin.longitude;

                button.setEnabled(false);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://unipool-app.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                TripService service = retrofit.create(TripService.class);

                if (noBlank(travelDestination, maxCapacity, meetingPoint, fare, departureTime)){
                    Trip trip = new Trip();
                    trip.setDriverId(Integer.parseInt(studentID));
                    trip.setDestination(travelDestination);
                    trip.setMaxCapacity(Integer.parseInt(maxCapacity));
                    trip.setMeetingLocation(meetingPoint);
                    trip.setFare(Integer.parseInt(fare));
                    trip.setOriginLatitude(originLatitude);
                    trip.setOriginLongitude(originLongitude);
                    trip.setDepartureTime(departureTime);

                    Call<Integer> call = service.registerTrip(trip);
                    call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if(!response.isSuccessful()){
                                try {
                                    Toast.makeText(HomeDriverActivity.this, "Error: " + response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                    button.setEnabled(true);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                Integer resp = response.body();
                                Toast.makeText(HomeDriverActivity.this, "Viaje registrado\nID viaje: " + resp.intValue(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), InTravelActivity.class);
                                intent.putExtra("trip_id", resp.intValue());
                                intent.putExtra("student_id", studentID);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            Toast.makeText(HomeDriverActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                            button.setEnabled(true);
                        }
                    });

                }else{
                    Toast.makeText(HomeDriverActivity.this, "Llena todos los campos, por favor.", Toast.LENGTH_SHORT).show();
                    button.setEnabled(true);
                }
            }
        });
    }

    private boolean noBlank(String travelDestination, String maxCapacity, String meetingPoint, String fare, String departureTime){
        if(!TextUtils.isEmpty(travelDestination) && !TextUtils.isEmpty(maxCapacity) && !TextUtils.isEmpty(meetingPoint) && !TextUtils.isEmpty(fare) && !TextUtils.isEmpty(departureTime)){
            return true;
        }else{
            return false;
        }

    }

    private void init(){
        textTravelDestination.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                || event.getAction() == KeyEvent.ACTION_DOWN || event.getAction() == KeyEvent.KEYCODE_ENTER){
                    geoLocate();
                }
                return false;
            }
        });
    }

    private void geoLocate(){
        String searchString = textTravelDestination.getEditText().getText().toString();
        Geocoder geocoder = new Geocoder(this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString,1);
        }catch (IOException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        if(list.size() > 0){
            Address address = list.get(0);
            destination = new LatLng(address.getLatitude(), address.getLongitude());
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), (float) 12.5);
            map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    origin = new LatLng(location.getLatitude(), location.getLongitude());
                    polylineOptions = new PolylineOptions().add(destination)
                            .add(new LatLng(location.getLatitude(), location.getLongitude()));
                    polyline = map.addPolyline(polylineOptions);

                }
            });

        }
    }

    private void moveCamera(LatLng latLng, float zoom){
        if(map.getCameraPosition().target != null){
            map.clear();
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        MarkerOptions markers = new MarkerOptions().position(latLng).title("Destino");
        map.addMarker(markers);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            buildGoogleApiClient();
            map.setMyLocationEnabled(true);
            init();

        }
    }

    public boolean checkUserLocationPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case Request_User_Location_Code:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        if(googleApiClient == null){
                            buildGoogleApiClient();
                        }
                        map.setMyLocationEnabled(true);
                    }
                }else{
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    protected synchronized void buildGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
