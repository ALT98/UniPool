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

public class MainActivity extends AppCompatActivity {
    private Button btnSignIn;
    private Button btnLogIn;
    private TextView textEmail;
    private TextView textPassword;


    DbConnection dbConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textEmail = findViewById(R.id.txtCorreo);
        textPassword = findViewById(R.id.txtPassword);

        dbConnection = new DbConnection(this, "UniPool", null, 1);

        btnLogIn = findViewById(R.id.btnLogin);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readUsers();
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

    public void readUsers(){
        String email = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();

        SQLiteDatabase db = dbConnection.getReadableDatabase();
        String[] parameters = {email};
        String[] campos = {Utilities.STUDENT_ID, Utilities.STUDENT_NAME, Utilities.DEPENDENCY, Utilities.EMAIL, Utilities.PASSWORD, Utilities.TYPE_OF_ACCOUNT};
        try{
            Cursor cursor = db.query(Utilities.USER_TABLE, campos, Utilities.EMAIL+ "= ? ", parameters, null,null,null);
            cursor.moveToFirst();
            Student student = new Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4) ,cursor.getInt(5));
            cursor.close();
            db.close();
            if(student.getEmail().equals(email) && student.getPassword().equals(password)){
                Toast.makeText(this, "Bienvenido " + student.getStudent_name(), Toast.LENGTH_SHORT).show();
                if(student.getTypeOfAccount() == 2){
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "Bienvenido " + student.getStudent_name() + ", tipo de Cuenta: Pasajero", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this,"Contraseña equivocada", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){
            Toast.makeText(this, "El usuario no existe.", Toast.LENGTH_LONG).show();
        }
    }


}
