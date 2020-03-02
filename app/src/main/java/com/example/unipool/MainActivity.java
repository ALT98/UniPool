package com.example.unipool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnSignIn;
    private Button btnLogIn;
    DbConnection dbConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        SQLiteDatabase db = dbConnection.getReadableDatabase();
        String[] parameters = {"FCFM"};
        String[] campos = {Utilities.STUDENT_ID, Utilities.STUDENT_NAME, Utilities.DEPENDENCY, Utilities.EMAIL, Utilities.TYPE_OF_ACCOUNT};
        try{
            Cursor cursor = db.query(Utilities.USER_TABLE, campos, Utilities.DEPENDENCY+ "= ?", parameters, null,null,null);
            cursor.moveToFirst();
            Student student = new Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4));
            Toast.makeText(this, student.toString(), Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(this, "No existe", Toast.LENGTH_LONG).show();
        }
    }


}
