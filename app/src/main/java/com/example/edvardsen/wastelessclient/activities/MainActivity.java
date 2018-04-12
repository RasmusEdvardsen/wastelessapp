package com.example.edvardsen.wastelessclient.activities;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


import com.example.edvardsen.wastelessclient.R;



public class MainActivity extends AppCompatActivity  {

    private Button scanBtn;
    private Button ocrBtn;
    private Button submitBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       scanBtn = (Button) findViewById(R.id.scanBtn);
       ocrBtn = (Button) findViewById(R.id.ocrBtn);


        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BarcodeActivity.class));
            }
        });

        ocrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OcrActivity.class));
            }
        });
    }


}
