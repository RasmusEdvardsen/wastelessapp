package com.example.edvardsen.wastelessclient.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.edvardsen.wastelessclient.R;
import com.example.edvardsen.wastelessclient.miscellaneous.Constants;
import com.example.edvardsen.wastelessclient.services.JSONService;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button login;
    Button signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TODO: NEW CLASS THAT CHECK IF UTIL.GETTOKEN THEN START MAIN ACT INSTEAD OF LOGIN ACT

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.signupButton);

        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                final String emailInput = email.getText().toString();
                final String passwordInput = password.getText().toString();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            // Create URL
                            URL loginURL = new URL(Constants.baseURL + Constants.loginPath + "/?email=" + emailInput + "&password=" + passwordInput);

                            // Create connection
                            HttpURLConnection httpURLConnection = (HttpURLConnection) loginURL.openConnection();
                            Log.i("information", String.valueOf(httpURLConnection.getResponseCode()));

                            if(httpURLConnection.getResponseCode() == 200){
                                JSONObject jsonObject = JSONService.toJSONObject(httpURLConnection.getInputStream());
                                Log.i("information", jsonObject.toString());
                                startActivity(new Intent(getBaseContext(), MainActivity.class));
                            }

                        }catch (Exception e){
                            Log.e("information", e.toString());
                        }
                    }
                });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailInput = email.getText().toString();
                final String passwordInput = password.getText().toString();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            // Create URL
                            URL loginURL = new URL(Constants.baseURL + Constants.loginPath + "/?email=" + emailInput + "&password=" + passwordInput);

                            // Create connection
                            HttpURLConnection httpURLConnection = (HttpURLConnection) loginURL.openConnection();
                            Log.i("information", String.valueOf(httpURLConnection.getResponseCode()));

                            if(httpURLConnection.getResponseCode() == 200){
                                JSONObject jsonObject = JSONService.toJSONObject(httpURLConnection.getInputStream());
                                Log.i("information", jsonObject.toString());
                            }

                        }catch (Exception e){
                            Log.e("information", e.toString());
                        }
                    }
                });
            }
        });
    }
}
