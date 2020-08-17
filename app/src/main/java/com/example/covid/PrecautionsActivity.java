package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.LinearLayout;

public class PrecautionsActivity extends AppCompatActivity {
    RecyclerView precautionsrecycleView;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precautions);

        //precautions

        Precautions[] precautions = new Precautions[]
                {
                        new Precautions("Hand Wash",R.drawable.handwash,"Regularly and thoroughly clean your hands with an alcohol-based hand rub or wash them with soap and water."),
                        new Precautions("Stay Home",R.drawable.virus,"Stay home and self-isolate even with minor symptoms such as cough, headache, mild fever, until you recover."),
                        new Precautions("Social Distance",R.drawable.distance,"Maintain at least 1 metre (3 feet) distance between yourself and others."),
                        new Precautions("Mask",R.drawable.prevention,"Before putting on a mask, clean hands with alcohol-based hand rub or soap and water."),
                        new Precautions("Sanitizer",R.drawable.alcohol,"To protect yourself and others against COVID-19, clean your hands frequently and thoroughly. Use alcohol-based hand sanitizer or wash your hands with soap and water. If you use an alcohol-based hand sanitizer, make sure you use and store it carefully."),

                };

        precautionsrecycleView = (RecyclerView) findViewById(R.id.recyleviewspreacutions);
        precautionsrecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
        PrecautionsAdapter precautionsAdapter = new PrecautionsAdapter(precautions);
        precautionsrecycleView.setAdapter(precautionsAdapter);

    }
}