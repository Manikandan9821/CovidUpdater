package com.example.covid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView Recovered,InfectedCases,Deaths,KnowSymptoms,KnowPrecautions;

    private Toolbar toolbar;

    Button KnowCorona;

    RecyclerView symptomsrecyclerView,precautionsrecycleView;

    AlertDialog.Builder builder;

    ImageButton CountiresGo;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Toolbar
        toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Covid-19");

        builder = new AlertDialog.Builder(this);

        Recovered = (TextView) findViewById(R.id.recovered);
        InfectedCases =(TextView) findViewById(R.id.infected);
        Deaths = (TextView) findViewById(R.id.deaths);
        KnowCorona = (Button) findViewById(R.id.view_btn_corona);

        KnowSymptoms = (TextView) findViewById(R.id.btnviewsymptoms);
        KnowPrecautions = (TextView) findViewById(R.id.btnviewPrecautions);


        //Go to countries Activity
        CountiresGo = (ImageButton) findViewById(R.id.btn_countires_go);
        CountiresGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent countiresintent = new Intent(MainActivity.this,CountriesActivity.class);
                startActivity(countiresintent);
            }
        });

        fetchlst();


          //symptoms
        Symptoms[] symptoms = new Symptoms[]
        {
                new Symptoms("Dry cough","Dry coughs are often caused by viral illnesses such as colds and flu, but they can also be caused by allergies or throat irritants..",R.drawable.cough),
                new Symptoms("Fever","A fever can cause a person to feel very uncomfortable.Signs and symptoms of a fever include the following Shivering, shaking, and chills.",R.drawable.fever),
                new Symptoms("Head Ache","Headaches can have causes that aren't due to underlying disease. Examples include lack of sleep, an incorrect eyeglass prescription, stress, loud noise exposure or tight head wear.",R.drawable.pain),
                new Symptoms("Sore Throat","Your throat or tonsils might also look red. Sometimes, white patches or areas of pus will form on the tonsils.",R.drawable.sorethrought),
        };

        symptomsrecyclerView  = (RecyclerView) findViewById(R.id.symptoms_recyleview);
        SymptomsAdapter symptomsadapter = new SymptomsAdapter(symptoms);
        symptomsrecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false));
        symptomsrecyclerView.setAdapter(symptomsadapter);

      //precautions

      Precautions[] precautions = new Precautions[]
              {
                      new Precautions("Hand Wash",R.drawable.handwash,"Regularly and thoroughly clean your hands with an alcohol-based hand rub or wash them with soap and water."),
                      new Precautions("Stay Home",R.drawable.virus,"Stay home and self-isolate even with minor symptoms such as cough, headache, mild fever, until you recover."),
                      new Precautions("Social Distance",R.drawable.distance,"Maintain at least 1 metre (3 feet) distance between yourself and others."),
                      new Precautions("Mask",R.drawable.prevention,"Before putting on a mask, clean hands with alcohol-based hand rub or soap and water."),
                      new Precautions("Sanitizer",R.drawable.alcohol,"To protect yourself and others against COVID-19, clean your hands frequently and thoroughly. Use alcohol-based hand sanitizer or wash your hands with soap and water. If you use an alcohol-based hand sanitizer, make sure you use and store it carefully."),


              };

      precautionsrecycleView = (RecyclerView) findViewById(R.id.recyleviewpreacutions);
      PrecautionsAdapter precautionsAdapter = new PrecautionsAdapter(precautions);
      precautionsrecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false));
      precautionsrecycleView.setAdapter(precautionsAdapter);


      //knoew corona

        KnowCorona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Knowa = new Intent(MainActivity.this,KnowAbtCorona.class);
                startActivity(Knowa);
            }
        });



        //view Symptoms
        KnowSymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent symp = new Intent(MainActivity.this,SymptomsActivity.class);
                startActivity(symp);
            }
        });


        //view Precautions
        KnowPrecautions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pre = new Intent(MainActivity.this,PrecautionsActivity.class);
                startActivity(pre);
            }
        });

    }

    private void fetchlst() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<String> call = api.getdata();

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(MainActivity.this,R.style.AppCompatAlertDialogStyle);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("loading....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setCancelable(false);
        progressDoalog.setCanceledOnTouchOutside(false);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                progressDoalog.dismiss();
                if (response.isSuccessful())
                {
                    if (response.body() != null)
                    {
                        SharedPreferences sp=getSharedPreferences("login",MODE_PRIVATE);
                        SharedPreferences.Editor e= sp.edit();
                        // Reading from SharedPreferences
                        String value1 = sp.getString("username", "");
                        String value2 = sp.getString("email", "");


                        Toast toast = Toast.makeText(getApplicationContext(),"Welcome " +value1,Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();

                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        data(jsonresponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                progressDoalog.dismiss();
                Log.i("Error",t.getMessage());
                Toast.makeText(getApplicationContext(),"Something Went Wrong!...",Toast.LENGTH_SHORT).show();
            }
        });

           }

    private void data(String response)
    {

    try
     {
         JSONObject obj = new JSONObject(response);
         Recovered.setText(obj.getString("recovered"));
         InfectedCases.setText(obj.getString("cases"));
         Deaths.setText(obj.getString("deaths"));
         Log.i("DATA",response.toString());
     }

     catch (Exception e)
     {

     }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        super.onOptionsItemSelected(item);

        //logout
        if (item.getItemId() == R.id.main_logout)
        {
            SharedPreferences sp=getSharedPreferences("login",MODE_PRIVATE);
            SharedPreferences.Editor e=sp.edit();
            e.clear();
            e.commit();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }

        //settings
        if (item.getItemId() == R.id.main_setting)
        {
            SettingsActivity();
        }
        return true;
    }

    private void logout()
    {


        //Uncomment the below code to Set the message and title from the strings.xml file
        builder.setMessage("Are Sure To Logout ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        Toast.makeText(getApplicationContext(),"Logout",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();

                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Logout");
        alert.show();



    }

    private void SettingsActivity()
    {

    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
}
