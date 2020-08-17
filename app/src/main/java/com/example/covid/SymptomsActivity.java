package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.LinearLayout;

public class SymptomsActivity extends AppCompatActivity {

    RecyclerView symptomsrecyclerView,precautionsrecycleView;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);


        //symptoms
        Symptoms[] symptoms = new Symptoms[]
                {
                        new Symptoms("Dry cough","Dry coughs are often caused by viral illnesses such as colds and flu, but they can also be caused by allergies or throat irritants..",R.drawable.cough),
                        new Symptoms("Fever","A fever can cause a person to feel very uncomfortable.Signs and symptoms of a fever include the following Shivering, shaking, and chills.",R.drawable.fever),
                        new Symptoms("Head Ache","Headaches can have causes that aren't due to underlying disease. Examples include lack of sleep, an incorrect eyeglass prescription, stress, loud noise exposure or tight head wear.",R.drawable.pain),
                        new Symptoms("Sore Throat","Your throat or tonsils might also look red. Sometimes, white patches or areas of pus will form on the tonsils.",R.drawable.sorethrought),
                };

        symptomsrecyclerView  = (RecyclerView) findViewById(R.id.symp_recyleview);
       // symptomsrecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
        SymptomsAdapter symptomsadapter = new SymptomsAdapter(symptoms);
        symptomsrecyclerView.setAdapter(symptomsadapter);

    }
}