package com.example.edvardsen.wastelessclient.services;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Epico-u-01 on 4/7/2018.
 */

public class LayoutService {
    public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if ( view instanceof ViewGroup ) {
            ViewGroup group = (ViewGroup)view;

            for ( int idx = 0 ; idx < group.getChildCount() ; idx++ ) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }
}
