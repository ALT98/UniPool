package com.example.unipool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInFormActivity extends AppCompatActivity {
/*
*  ------- Intent -------
*   2 Pasajero
*   1 Conductor
*
* */

    private TextView textName;
    private TextView textStudent_id;
    private TextView textDependency;
    private TextView textEmail;
    private TextView textPassword;
    private TextView textPhone_Number;
    private Button buttonNewAccount;
    private ImageView imgNewAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_form);
        this.setTitle(R.string.newAccount);

        textName = findViewById(R.id.textName);
        textStudent_id = findViewById(R.id.textStudentId);
        textDependency = findViewById(R.id.textDependency);
        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        buttonNewAccount = findViewById(R.id.btnNewAccount);
        imgNewAccount = findViewById(R.id.imgNewAccount);
        textPhone_Number = findViewById(R.id.textPhone_Number);

        Intent intent = getIntent();
        final String typeOfAccount = intent.getStringExtra("typeOfAccount");

        buttonNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonNewAccount.setEnabled(false);
                createAccount(typeOfAccount);
            }
        });
    }

    public void createAccount(String typeOfAccount){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://unipool-app.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final StudentService service = retrofit.create(StudentService.class);

        String name = textName.getText().toString().trim();
        String student_id = textStudent_id.getText().toString().trim();
        String dependency = textDependency.getText().toString().trim();
        String email = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();
        String phone_number = textPhone_Number.getText().toString().trim();

        if(emailVerification(email)){
            try{
                Student student = new Student(name, Integer.parseInt(student_id), dependency, email, password, Integer.parseInt(typeOfAccount), phone_number);
                Call<Integer> createCall = service.create(student);
                createCall.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> _, Response<Integer> resp) {
                        if(!resp.isSuccessful()){
                            try {
                                Toast.makeText(SignInFormActivity.this, "Error: " + resp.errorBody().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(SignInFormActivity.this, "Nueva cuenta creada", Toast.LENGTH_LONG).show();
                            Intent goHome = new Intent(SignInFormActivity.this, MainActivity.class);
                            startActivity(goHome);
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> _, Throwable t) {
                        String error = t.getCause().toString();
                        Toast.makeText(SignInFormActivity.this, error, Toast.LENGTH_LONG).show();
                        buttonNewAccount.setEnabled(true);
                    }
                });
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }else{
            buttonNewAccount.setEnabled(true);
        }
    }

    public boolean emailVerification(String email){
        String domain = "uanl.edu.mx";
        if(isValidEmailAddress(email)){
            if(email.contains(domain)){
                return true;
            }else{
                Toast.makeText(this, "Favor de registrar el correo universitario.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{
            Toast.makeText(this, "Ingresa un correo válido", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public boolean validateForm(String name, String mat, String dependency, String email, String password, String typeOfAccount){
        return true;
    }

    public void cleanTextViews(){
        textName.setText("");
        textPassword.setText("");
        textDependency.setText("");
        textEmail.setText("");
        textStudent_id.setText("");
    }

}
