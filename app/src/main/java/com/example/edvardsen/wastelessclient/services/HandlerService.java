package com.example.edvardsen.wastelessclient.services;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.edvardsen.wastelessclient.activities.OcrActivity;
import com.example.edvardsen.wastelessclient.activities.OcrResultActivity;

import java.util.List;

/**
 * Created by Epico-u-01 on 4/7/2018.
 */

//TODO: MAKE POST AND POSTDELAYED HANDLERS THAT TAKE A SERVICE.SOMEMETHODTODO ( = SOME NUMBER), THEN GO THROUGH SWITCH CASE AND FIND METHOD TO CALL.
public class HandlerService {
    public static void setVisibility(final View view, final Integer visibility){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(visibility);
            }
        });
    }

    public static void setBuilder(final Context ctx,final String[] scanResults){
        Log.i("information", "HANDLER SERVICE A");
        new Handler(Looper.getMainLooper()).post(new Runnable(){



            @Override
            public void run() {

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ctx);
                Log.i("information", " RUN LOG INSIDE HANDLER");
                builder.setItems(scanResults, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //the variable i is the chosen item in the dialog!
                        Log.i("information", "LOG INSIDE HANDLER SERVICE");
                        Log.i("information", String.valueOf(i));
                        Intent intent = new Intent(ctx, OcrResultActivity.class);
                        intent.putExtra("choice", i);
                        ctx.startActivity(new Intent(ctx, OcrResultActivity.class));

                    }
                });
                final EditText input = new EditText(ctx);

                builder.setNeutralButton("Manually Enter Name ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        input.setText(input.getText());
                        //secondDialog(result);
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //startActivity(new Intent(getBaseContext(), OcrActivity.class));
                    }
                });

                //builder.setMessage(result.getText());
                android.support.v7.app.AlertDialog alert1 = builder.create();
                alert1.show();
            }
        });
    }

    public static void setVisibility(final View view, final Integer visibility, int delay){
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(visibility);
            }
        }, delay);
    }

    public static void makeToast(final Context context,final String text,final int duration) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, duration).show();
            }
        });
    }

    public static void makeToast(final Context context,final String text,final int duration, int delay) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, duration).show();
            }
        }, delay);
    }
}
