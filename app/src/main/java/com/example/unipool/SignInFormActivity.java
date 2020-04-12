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
    private TextView textPassword;
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

        Intent intent = getIntent();
        final String typeOfAccount = intent.getStringExtra("typeOfAccount");

        buttonNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(typeOfAccount);
            }
        });
    }

    public void createAccount(String typeOfAccount){
        String name = textName.getText().toString().trim();
        String student_id = textStudent_id.getText().toString().trim();
        String dependency = textDependency.getText().toString().trim();
        String email = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();

        if(validateForm(name, student_id, dependency, email, password, typeOfAccount) && !isDuplicatedEmail(email)){
            DbConnection dbConnection = new DbConnection(this, "UniPool", null, 1);
            SQLiteDatabase db =   dbConnection.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(Utilities.STUDENT_ID, Integer.parseInt(student_id));
            values.put(Utilities.STUDENT_NAME, name);
            values.put(Utilities.DEPENDENCY, dependency);
            values.put(Utilities.EMAIL, email);
            values.put(Utilities.PASSWORD, password);
            values.put(Utilities.TYPE_OF_ACCOUNT, typeOfAccount);

            Long result = db.insert(Utilities.USER_TABLE, Utilities.STUDENT_ID, values);
            db.close();

            Toast.makeText(this, "Se ha creado tu cuenta.", Toast.LENGTH_SHORT).show();

            Intent goLogin = new Intent(this, MainActivity.class);
            startActivity(goLogin);
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
        if(!name.isEmpty() && !mat.isEmpty() && !dependency.isEmpty() && !email.isEmpty() && !password.isEmpty() && !typeOfAccount.isEmpty()){
            if(emailVerification(email)){
                if(TextUtils.isDigitsOnly(mat)){
                    if(!isDuplicatedEmail(email)){
                        if (!isDuplicatedStudent_Id(mat)){
                            return true;
                        }else{
                            Toast.makeText(this, "La matricula ya existe", Toast.LENGTH_LONG).show();
                            return false;
                        }
                    }else{
                        Toast.makeText(this, "Este correo ya está registrado", Toast.LENGTH_LONG).show();
                        return false;
                    }
                }else{
                    Toast.makeText(this, "La matricula debe ser numérica", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else{
                return false;
            }
        }else{
            Toast.makeText(this, "Favor de llenar todos los campos", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean isDuplicatedEmail(String email){
        DbConnection dbConnection = new DbConnection(this, "UniPool", null, 1);
        SQLiteDatabase db = dbConnection.getReadableDatabase();
        String[] parameters = {email};
        String[] campos = {Utilities.EMAIL};
        String compare;
        try{
            Cursor cursor = db.query(Utilities.USER_TABLE, campos, Utilities.EMAIL+ "= ? ", parameters, null,null,null);
            cursor.moveToFirst();
            compare = cursor.getString(0);
            cursor.close();
            db.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean isDuplicatedStudent_Id(String student_id){
        DbConnection dbConnection = new DbConnection(this, "UniPool", null, 1);
        SQLiteDatabase db = dbConnection.getReadableDatabase();
        String[] parameters = {student_id};
        String[] campos = {Utilities.STUDENT_ID};
        try{
            Cursor cursor = db.query(Utilities.USER_TABLE, campos, Utilities.STUDENT_ID+ "= ? ", parameters, null,null,null);
            cursor.moveToFirst();
            int aux = cursor.getInt(0);
            cursor.close();
            db.close();
            if(aux == Integer.parseInt(student_id)){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            return false;
        }
    }

    public void cleanTextViews(){
        textName.setText("");
        textPassword.setText("");
        textDependency.setText("");
        textEmail.setText("");
        textStudent_id.setText("");
    }

}
