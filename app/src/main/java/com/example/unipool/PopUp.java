package com.example.unipool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PopUp extends Activity {

    Button btnJoin;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_layout);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://unipool-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final TripsService service = retrofit.create(TripsService.class);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width *.8), (int) (height*.3));

        Intent recived = getIntent();
        final int travel_id = Integer.parseInt(recived.getStringExtra("travel_id"));
        final int student_id = Integer.parseInt(recived.getStringExtra("student_id"));

        textView = findViewById(R.id.textView2);
        textView.setText(textView.getText() + " con el ID: " + travel_id);

        btnJoin = findViewById(R.id.button3);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnJoin.setEnabled(false);
                TripPassenger trip = new TripPassenger(travel_id, student_id);

                Call<Integer> createCall = service.joinTrip(trip);
                createCall.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        btnJoin.setEnabled(true);
                        Intent intent = new Intent(PopUp.this, InStudentTravelActivity.class);
                        intent.putExtra("trip_id", travel_id);
                        intent.putExtra("student_id", student_id);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        btnJoin.setEnabled(true);
                    }
                });
            }
        });

    }

}
