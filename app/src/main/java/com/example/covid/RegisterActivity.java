package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private RelativeLayout rlayout;
    private Animation animation;
    private EditText Username,Email,Password,Cpassword;
    private Button Register;
    SharedPreferences sp;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.bgHeader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sp=getSharedPreferences("login",MODE_PRIVATE);

        //vibrator
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        rlayout = findViewById(R.id.rlayout);
        animation = AnimationUtils.loadAnimation(this, R.anim.uptodowndiagonal);
        rlayout.setAnimation(animation);

        Username = (EditText) findViewById(R.id.rname);
        Email = (EditText) findViewById(R.id.remail);
        Password = (EditText) findViewById(R.id.rpassword);
        Cpassword = (EditText) findViewById(R.id.retypepassword);
        Register = (Button) findViewById(R.id.register);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();

            }
        });
    }

    private void validateData()
    {
        String username = Username.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String confrim_password = Cpassword.getText().toString().trim();

        //checking if email is empty
        if (TextUtils.isEmpty(email))
        {
            Email.setError("Please enter Email");
            Email.requestFocus();
            // Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }
        if (TextUtils.isEmpty(username))
        {
            Username.setError("Please enter UserName");
            Username.requestFocus();
            // Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }

        if (TextUtils.isEmpty(confrim_password))
        {
            Cpassword.setError("Please enter Confrim Password");
            Cpassword.requestFocus();
            // Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }
        //checking if password is empty
        if (TextUtils.isEmpty(password))
        {
            Password.setError("Please enter Password");
            Password.requestFocus();
            //Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }

        if(!password.equals(confrim_password))
        {
            //are equal
            Toast.makeText(getApplicationContext(),"Passwords are not matches",Toast.LENGTH_SHORT).show();
            //Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }

        //validating email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Email.setError("Enter a valid email");
            Email.requestFocus();
            //Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }

        register();

    }

    private void register()
    {
        String username = Username.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String confrim_password = Cpassword.getText().toString().trim();
        String password = Password.getText().toString().trim();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RegisterInterface.REGIURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        RegisterInterface api = retrofit.create(RegisterInterface.class);

        Call<String> call = api.getUserRegi(username,email,password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        // Toast.makeText(getApplicationContext(),"success"+response.body(),Toast.LENGTH_LONG).show();
                        String jsonresponse = response.body().toString();
                        parseRegData(jsonresponse);

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");
                        //Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                Log.i("unSuccess", t.toString());
            }
        });


    }

    private void parseRegData(String response)  {

        try {

            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("status").equals("true"))
            {
                saveInfo(response);
                Toast.makeText(RegisterActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                this.finish();
            }
            else
            {
                Toast.makeText(RegisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        }
        catch (JSONException e)
        {

        }
    }

    private void saveInfo(String response)
    {

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++)
                {
                    JSONObject dataobj = dataArray.getJSONObject(i);

                   /* SharedPreferences.Editor e=sp.edit();
                    e.putString("username",dataobj.getString("username"));
                    e.putString("email",dataobj.getString("email"));
                    e.commit();*/
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}