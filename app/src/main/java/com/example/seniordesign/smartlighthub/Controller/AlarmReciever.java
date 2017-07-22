package com.example.seniordesign.smartlighthub.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jamesharrison on 7/18/17.
 */

public class AlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm went off", Toast.LENGTH_SHORT).show();
        Log.d("Alarm went off","time");

    }
}
