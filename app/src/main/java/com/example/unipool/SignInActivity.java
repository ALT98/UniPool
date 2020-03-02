package com.example.unipool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private ImageView imgPassenger;
    private ImageView imgDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        this.setTitle(R.string.newAccount);

        imgPassenger = findViewById(R.id.imgPassenger);
        imgDriver = findViewById(R.id.imgDriver);

        imgPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPassenger = new Intent(SignInActivity.this, SignInFormActivity.class);
                intentPassenger.putExtra("typeOfAccount", "1");
                startActivity(intentPassenger);
            }
        });

        imgDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDriver = new Intent(SignInActivity.this, SignInFormActivity.class);
                intentDriver.putExtra("typeOfAccount", "2");
                startActivity(intentDriver);
            }
        });

    }
}
