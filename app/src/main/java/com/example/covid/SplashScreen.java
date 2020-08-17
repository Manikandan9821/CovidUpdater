package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SplashScreen extends AppCompatActivity {

    private ImageButton Btn_Go;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Btn_Go = (ImageButton) findViewById(R.id.btn_go);

        Btn_Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(SplashScreen.this,LoginActivity.class);
                startActivity(go);
            }
        });
    }
}
