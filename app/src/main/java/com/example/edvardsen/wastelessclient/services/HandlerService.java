package com.example.edvardsen.wastelessclient.services;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Epico-u-01 on 4/7/2018.
 */

public class HandlerService {
    public static void setVisibility(View view, Integer visibility){
        final View viewToSet = view; final Integer visibilityToSet = visibility;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                viewToSet.setVisibility(visibilityToSet);
            }
        });
    }

    public static void makeToast(Context baseContext, String s, int lengthShort) {
        final Context baseCtx = baseContext; final String string = s; final int length = lengthShort;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(baseCtx, string, length).show();
            }
        });
    }
}
