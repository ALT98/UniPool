package com.example.unipool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnSignIn;
    private Button btnLogIn;
    private TextView textEmail;
    private TextView textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://unipool-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final StudentService service = retrofit.create(StudentService.class);
        final DefinitiveTripsService serviceTrips = retrofit.create(DefinitiveTripsService.class);
        final StudentCurrentTripService serviceStudent = retrofit.create(StudentCurrentTripService.class);

        textEmail = findViewById(R.id.txtCorreo);
        textPassword = findViewById(R.id.txtPassword);

        btnLogIn = findViewById(R.id.btnLogin);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textEmail.getText().toString().trim();
                String password = textPassword.getText().toString().trim();
                Login(service, serviceTrips, serviceStudent, email, password);
            }
        });


        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignIn();
            }
        });
    }

    public void openSignIn(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void Login(final StudentService service, final DefinitiveTripsService tripsService, final StudentCurrentTripService studentService, final String email, final String password) {
        btnLogIn.setEnabled(false);
        Call<Student> createCall = service.Login(email, password);
        createCall.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if(response.isSuccessful()){

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                    final Student testStudent = response.body();
                    if(testStudent.getEmail().equals(email)){
                        String typeOfAccount = "" + testStudent.getTypeOfAccount();
                        final String studentID = "" + testStudent.getStudent_id();
                        final String studentName = testStudent.getStudent_name();
                        if(typeOfAccount.equals("1")){
                            Call<DefinitiveTripsClass> newCall = tripsService.getCurrentTrip(testStudent.getStudent_id());
                            newCall.enqueue(new Callback<DefinitiveTripsClass>() {
                                @Override
                                public void onResponse(Call<DefinitiveTripsClass> call, Response<DefinitiveTripsClass> response) {
                                    if(response.isSuccessful()){
                                        DefinitiveTripsClass test = response.body();
                                        if(test.getTripId() > 0){
                                            Intent currentTrip = new Intent(MainActivity.this, InTravelActivity.class);
                                            currentTrip.putExtra("trip_id", test.getTripId());
                                            currentTrip.putExtra("studentID", testStudent.getStudent_id());
                                            btnLogIn.setEnabled(true);
                                            startActivity(currentTrip);
                                            return;
                                        }
                                    }else{
                                        Intent intent = new Intent(MainActivity.this, HomeDriverActivity.class);
                                        intent.putExtra("studentName", studentName);
                                        intent.putExtra("studentID", studentID);
                                        btnLogIn.setEnabled(true);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailure(Call<DefinitiveTripsClass> call, Throwable t) {
                                    Toast.makeText(MainActivity.this, "something", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else if(typeOfAccount.equals("2")){
                            Call<StudentCurrentTrip> studentCall = studentService.getCurrentTrip(testStudent.getStudent_id());
                            studentCall.enqueue(new Callback<StudentCurrentTrip>() {
                                @Override
                                public void onResponse(Call<StudentCurrentTrip> call, Response<StudentCurrentTrip> response) {
                                    if(response.isSuccessful()){
                                        StudentCurrentTrip test = response.body();
                                        if(test.getTripId() > 0){
                                            Intent currentTrip = new Intent(MainActivity.this, InStudentTravelActivity.class);
                                            currentTrip.putExtra("trip_id", test.getTripId());
                                            currentTrip.putExtra("student_id", testStudent.getStudent_id());
                                            startActivity(currentTrip);
                                            btnLogIn.setEnabled(true);
                                            return;
                                        }
                                    }else{
                                        Intent intent = new Intent(MainActivity.this, StudentTravelActivity.class);
                                        intent.putExtra("studentName", studentName);
                                        intent.putExtra("studentID", studentID);
                                        btnLogIn.setEnabled(true);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailure(Call<StudentCurrentTrip> call, Throwable t) {
                                    Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }else{
                    if(response.code() == 500){
                        Toast.makeText(MainActivity.this, "Unhandled exception: User does not exist", Toast.LENGTH_SHORT).show();
                        btnLogIn.setEnabled(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(MainActivity.this, "I'm in troubles to get Internet... Try again.", Toast.LENGTH_SHORT).show();
                btnLogIn.setEnabled(true);
            }
        });
    }

}
