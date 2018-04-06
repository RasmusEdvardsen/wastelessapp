package com.example.edvardsen.wastelessclient.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.edvardsen.wastelessclient.R;
import com.example.edvardsen.wastelessclient.miscellaneous.Constants;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    EditText email;
    EditText password;
    Button login;
    Button signup;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        login = view.findViewById(R.id.loginButton);
        signup = view.findViewById(R.id.signupButton);

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
                            httpURLConnection.connect();
                            Log.i("information", "asd");

                            //Read JSON //TODO: PUT IN JSON SERVICE IN MISCELLANEOUS
                            BufferedReader streamReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                            StringBuilder responseStrBuilder = new StringBuilder();
                            String inputStr;
                            while ((inputStr = streamReader.readLine()) != null)
                                responseStrBuilder.append(inputStr);
                            JSONArray json = new JSONArray(responseStrBuilder.toString());

                            Log.i("information", json.toString());
                        }catch (Exception e){
                            Log.e("loginasync", e.toString());
                        }
                    }
                });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailInput = email.getText().toString();
                String passwordInput = password.getText().toString();
            }
        });
    }
}