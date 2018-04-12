package com.example.edvardsen.wastelessclient.services;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
