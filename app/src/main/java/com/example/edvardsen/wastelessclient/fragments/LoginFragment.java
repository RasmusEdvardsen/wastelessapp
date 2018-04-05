package com.example.edvardsen.wastelessclient.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.edvardsen.wastelessclient.R;
import com.example.edvardsen.wastelessclient.miscellaneous.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
                            URL loginURL = new URL("http://192.168.43.229:64354/api/users/?email=asd&password=asd");

                            // Create connection
                            HttpURLConnection httpURLConnection = (HttpURLConnection) loginURL.openConnection();

                            Log.i("information", "asd");
                            InputStream responseBody = httpURLConnection.getInputStream();
                            InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");

                            Log.i("information", "zxc");

                            JsonReader jsonReader = new JsonReader(responseBodyReader);
                            String output = "";
                            jsonReader.beginObject(); // Start processing the JSON object
                            while (jsonReader.hasNext()) { // Loop through all keys
                                String key = jsonReader.nextName(); // Fetch the next key
                                String value = jsonReader.nextString();
                                output += key + ": ";
                                output += value + "\n";
                            }
                            Log.i("information", output);
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
                new Signup().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "http://localhost:64354/api/users/?email=asd&password=asd");
            }
        });
    }

    public static class Login extends AsyncTask<String, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArray = null;
            Response response = null;
            try{
                try {
                    String url = params[0];
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    response = client.newCall(request).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(response == null) return null;
                jsonArray = new JSONArray(response.body().string());
            }catch (Exception e){
                //TODO: log
                Log.e("loginauthbg", e.toString());
            }
            if(response != null){
                if(response.code() == 200){
                    return jsonArray;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            try{
                if(jsonArray == null){
                    Log.i("information", "jsonArray is null");
                }else{
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    Log.i("rspbody", jsonObject.toString());
                }
            }catch (Exception e) {e.printStackTrace();}
        }
    }

    public static class Signup extends AsyncTask<String, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArray = null;
            Response response = null;
            try{
                try {
                    String url = params[0];
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    response = client.newCall(request).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(response == null) return null;
                jsonArray = new JSONArray(response.body().string());
            }catch (Exception e){
                //TODO: log
                Log.e("loginauthbg", e.toString());
            }
            if(response != null){
                if(response.code() == 200){
                    return jsonArray;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            try{
                if(jsonArray == null){
                    Log.i("information", "jsonArray is null");
                }else{
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    Log.i("rspbody", jsonObject.toString());
                }
            }catch (Exception e) {e.printStackTrace();}
        }
    }

}