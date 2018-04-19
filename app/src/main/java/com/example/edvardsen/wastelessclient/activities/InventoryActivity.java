package com.example.edvardsen.wastelessclient.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.example.edvardsen.wastelessclient.R;
import com.example.edvardsen.wastelessclient.miscellaneous.GridAdapter;

public class InventoryActivity extends AppCompatActivity {

    GridView gridView;

    String[] values = {
            "bread",
            "coldmeat",
            "eggs",
            "fish",
            "lettuce",
            "meat",
            "milk",
            "onion",
            "steak"
    };

    int[] images = {
            R.drawable.bread,
            R.drawable.coldmeat,
            R.drawable.eggs,
            R.drawable.fish,
            R.drawable.lettuce,
            R.drawable.meat,
            R.drawable.milk,
            R.drawable.onion,
            R.drawable.steak
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory);

        gridView = (GridView)findViewById(R.id.gridview);
        GridAdapter gridAdapter = new GridAdapter(this,values,images);
        gridView.setAdapter(gridAdapter);
    }


}
