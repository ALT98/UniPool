package com.example.unipool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignInFormActivity extends AppCompatActivity {
/*
*  ------- Intent -------
*   1 Pasajero
*   2 Conductor
*
* */

    private TextView textName;
    private TextView textStudent_id;
    private TextView textDependency;
    private TextView textEmail;
    private Button buttonNewAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_form);
        this.setTitle(R.string.newAccount);

        textName = findViewById(R.id.textName);
        textStudent_id = findViewById(R.id.textStudentId);
        textDependency = findViewById(R.id.textDependency);
        textEmail = findViewById(R.id.textEmail);
        buttonNewAccount = findViewById(R.id.btnNewAccount);

        Intent intent = getIntent();
        final String typeOfAccount = intent.getStringExtra("typeOfAccount");
        Toast.makeText(this, typeOfAccount, Toast.LENGTH_SHORT).show();

        buttonNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(typeOfAccount);
            }
        });
    }

    public void createAccount(String typeOfAccount){
        String name = textName.getText().toString().trim();
        String student_id = textStudent_id.getText().toString();
        String dependency = textDependency.getText().toString().trim();
        String email = textEmail.getText().toString().trim();

        if(!TextUtils.isEmpty(name)){
            DbConnection dbConnection = new DbConnection(this, "UniPool", null, 1);
            SQLiteDatabase db =   dbConnection.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(Utilities.STUDENT_ID, Integer.parseInt(student_id));
            values.put(Utilities.STUDENT_NAME, name);
            values.put(Utilities.DEPENDENCY, dependency);
            values.put(Utilities.EMAIL, email);
            values.put(Utilities.TYPE_OF_ACCOUNT, typeOfAccount);

            Long result = db.insert(Utilities.USER_TABLE, Utilities.STUDENT_ID, values);
            db.close();

            Toast.makeText(this, "ID registro: " + result, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(SignInFormActivity.this, "El nombre es requerido", Toast.LENGTH_SHORT).show();
        }
    }

}
