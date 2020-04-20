package com.example.unipool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button btnSignIn;
    private Button btnLogIn;
    private TextView textEmail;
    private TextView textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://unipool-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final StudentService service = retrofit.create(StudentService.class);

        textEmail = findViewById(R.id.txtCorreo);
        textPassword = findViewById(R.id.txtPassword);

        btnLogIn = findViewById(R.id.btnLogin);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textEmail.getText().toString().trim();
                String password = textPassword.getText().toString().trim();
                Login(service, email, password);
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

    public void Login(StudentService service, final String email, String password) {
        btnLogIn.setEnabled(false);
        Call<Student> createCall = service.Login(email, password);
        createCall.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if(response.isSuccessful()){
                    Student testStudent = response.body();
                    if(testStudent.getEmail().equals(email)){
                        String typeOfAccount = "" + testStudent.getTypeOfAccount();
                        String studentID = "" + testStudent.getStudent_id();
                        String studentName = testStudent.getStudent_name();
                        if(typeOfAccount.equals("2")){
                            Intent intent = new Intent(MainActivity.this, HomeDriverActivity.class);
                            intent.putExtra("studentName", studentName);
                            intent.putExtra("studentID", studentID);
                            startActivity(intent);
                        }else if(typeOfAccount.equals("1")){
                            Intent intent1 = new Intent(MainActivity.this, StudentTravelActivity.class);
                            intent1.putExtra("studentName", studentName);
                            intent1.putExtra("studentID", studentID);
                            startActivity(intent1);
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
