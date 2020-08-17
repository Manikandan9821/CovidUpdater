package com.example.covid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btRegister;
    private TextView tvLogin,forgot;
    private EditText Email,Password;
    private Button Login,btnregister;
    SharedPreferences sp;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp=getSharedPreferences("login",MODE_PRIVATE);

        //vibrator
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        if(sp.contains("username") && sp.contains("email"))
        {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
            //finish current activity
        }

        btRegister  = findViewById(R.id.btRegister);
        btnregister  = findViewById(R.id.btRegisterss);
        tvLogin     = findViewById(R.id.tvLogin);
        forgot = (TextView) findViewById(R.id.tvForgot);
        btRegister.setOnClickListener(this);
        btnregister.setOnClickListener(this);

        Email = (EditText) findViewById(R.id.lemail);
        Password = (EditText) findViewById(R.id.lpassword);
        Login = (Button) findViewById(R.id.loginn);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotintent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(forgotintent);
                finish();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }


    private void validateData()
    {
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        //checking if email is empty
        if (TextUtils.isEmpty(email))
        {
            Email.setError("Please enter email");
            Email.requestFocus();
            // Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }
        //checking if password is empty
        if (TextUtils.isEmpty(password))
        {
            Password.setError("Please enter password");
            Password.requestFocus();
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

        loginS();

    }

    private void loginS()
    {
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LoginInterface.LOGINURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        LoginInterface api = retrofit.create(LoginInterface.class);

        Call<String> call = api.getUserLogin(email,password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.i("Responsestring", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        parseLoginData(jsonresponse);

                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("unSuccess", t.toString());
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v==btRegister){
            Intent intent   = new Intent(LoginActivity.this,RegisterActivity.class);
            Pair[] pairs    = new Pair[1];
            pairs[0] = new Pair<View,String>(tvLogin,"tvLogin");
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
            startActivity(intent,activityOptions.toBundle());
        }
        if (v==btnregister){
            Intent intent   = new Intent(LoginActivity.this,RegisterActivity.class);
            Pair[] pairs    = new Pair[1];
            pairs[0] = new Pair<View,String>(tvLogin,"tvLogin");
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
            startActivity(intent,activityOptions.toBundle());
        }

    }

    private void parseLoginData(String response)
    {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                saveInfo(response);
                Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                this.finish();
            }
            else{
                Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

    }

    private void saveInfo(String response)
    {

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    SharedPreferences.Editor e=sp.edit();
                    e.putString("username",dataobj.getString("username"));
                    e.putString("email",dataobj.getString("email"));
                    e.commit();

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}