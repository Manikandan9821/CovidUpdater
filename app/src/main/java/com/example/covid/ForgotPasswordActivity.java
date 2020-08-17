package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText ForgotEmail,ForgotPasswords;
    private Button ForgotPasswordBtn;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ForgotEmail = (EditText) findViewById(R.id.foremail);
        ForgotPasswords = (EditText) findViewById(R.id.forpass);

        ForgotPasswordBtn = (Button) findViewById(R.id.forgotbtn);

        //vibrator
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        ForgotPasswordBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                validateData();
            }
        });
    }

    private void validateData()
    {
        final String forgot_email = ForgotEmail.getText().toString().trim();
        final String forgot_password = ForgotPasswords.getText().toString().trim();

        //checking if email is empty
        if (TextUtils.isEmpty(forgot_email))
        {
            ForgotEmail.setError("Please enter email");
            ForgotEmail.requestFocus();
            // Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }
        //checking if password is empty
        if (TextUtils.isEmpty(forgot_password))
        {
            ForgotPasswords.setError("Please enter password");
            ForgotPasswords.requestFocus();
            //Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }
        //validating email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(forgot_email).matches())
        {
            ForgotEmail.setError("Enter a valid email");
            ForgotEmail.requestFocus();
            //Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }

        forgotPassword(forgot_email,forgot_password);

    }

    private void forgotPassword(String forgot_email, String forgot_password)
    {
        final String reg_email = ForgotEmail.getText().toString();
        final String reg_password = ForgotPasswords.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RegisterInterface.REGIURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ForgotPassword api = retrofit.create(ForgotPassword.class);

        Call<String> call = api.getUserForgotPassword(reg_email,reg_password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful())
                {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        Toast.makeText(getApplicationContext(),"Password Successfully changed",Toast.LENGTH_LONG).show();
                        String jsonresponse = response.body().toString();
                        DAta(jsonresponse);


                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");
                        //Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("onEmptyResponse", t.getMessage());
                Toast.makeText(getApplicationContext(),"Password Not Successfully changed",Toast.LENGTH_LONG).show();

            }
        });

    }

    private void DAta(String response)
    {
        try{
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("status").equals("true"))
            {

              //  Toast.makeText(ForgotPasswordActivity.this, "Password Successfully Changed!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                this.finish();
            }
            else
            {
                Toast.makeText(ForgotPasswordActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        }
        catch (JSONException e)
        {

        }

    }

}