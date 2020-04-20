package com.example.unipool;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentTravelActivity extends AppCompatActivity {

    private TextInputLayout textSearchQuery;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_travel);

        textSearchQuery = findViewById(R.id.textSearchQuery);
        listView = findViewById(R.id.listView);

        textSearchQuery.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN || event.getAction() == KeyEvent.KEYCODE_ENTER){
                    String searchQuery = textSearchQuery.getEditText().getText().toString();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://unipool-app.herokuapp.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    TripsService service = retrofit.create(TripsService.class);

                    Call<Trips> createCall = service.searchTrips(searchQuery);

                    createCall.enqueue(new Callback<Trips>() {
                        @Override
                        public void onResponse(Call<Trips> call, Response<Trips> response) {
                            if(response.isSuccessful()){
                                Trips trips = response.body();
                                List<Trips> tripsList = trips.getTripsList();
                                List<String> destinations = new ArrayList<>();
                                List<String> departureTime = new ArrayList<>();
                                List<String> id = new ArrayList<>();
                                List<String> fare = new ArrayList<>();
                                List<String> seats = new ArrayList<>();
                                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                for(Trips t : tripsList){
                                    destinations.add(t.getDestination());
                                    id.add("" + t.getTripId());
                                    fare.add("" + t.getFare());
                                    seats.add("" + t.getAvailableSeats());
                                    try {
                                        Date d = input.parse(t.getDepartureTime());
                                        String formatted = output.format(d);
                                        t.setDepartureTime(formatted);
                                        departureTime.add(t.getDepartureTime());
                                    } catch (ParseException e) {
                                        Toast.makeText(StudentTravelActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                }
                                if(tripsList.toString().equals("[]")){
                                    ArrayAdapter arrayAdapter = new ArrayAdapter(StudentTravelActivity.this, android.R.layout.simple_list_item_1);
                                    arrayAdapter.add("No hay viajes pa ese rancho :c");
                                    listView.setAdapter(arrayAdapter);
                                    return;
                                }
                                String[] array1 = new String[destinations.size()];
                                String[] array2 = new String[departureTime.size()];
                                String[] array3 = new String[id.size()];
                                String[] array4 = new String[fare.size()];
                                String[] array5 = new String[seats.size()];

                                destinations.toArray(array1);
                                departureTime.toArray(array2);
                                id.toArray(array3);
                                fare.toArray(array4);
                                seats.toArray(array5);

                                MyAdapter adapter = new MyAdapter(StudentTravelActivity.this, array1, array2, array3, array4, array5);
                                listView.setAdapter(adapter);
                            }else{
                                switch (response.code()){
                                    case 500:
                                        Toast.makeText(StudentTravelActivity.this, "Error en el server", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<Trips> call, Throwable t) {

                        }
                    });
                }
                return false;
            }
        });
    }
    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String destinations[];
        String departureTime[];
        String tripId[];
        String fare[];
        String availableSeats[];

        MyAdapter(Context c, String destinations[], String departureTime[], String tripId[], String fare[], String availableSeats[]){
            super(c, R.layout.row, R.id.textViewDestination, destinations);
            this.context = c;
            this.destinations = destinations;
            this.departureTime = departureTime;
            this.tripId = tripId;
            this.fare = fare;
            this.availableSeats = availableSeats;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            TextView destination = row.findViewById(R.id.textViewDestination);
            TextView descriptions = row.findViewById(R.id.textViewTime);
            TextView id = row.findViewById(R.id.textViewId);
            TextView fareA = row.findViewById(R.id.textViewFare);
            TextView seats = row.findViewById(R.id.textViewSeats);

            destination.setText(destination.getText() + destinations[position]);
            descriptions.setText(descriptions.getText() + departureTime[position]);
            id.setText(id.getText() + tripId[position]);
            fareA.setText(fareA.getText() + fare[position]);
            seats.setText(seats.getText() + availableSeats[position]);

            return row;
        }
    }
}
