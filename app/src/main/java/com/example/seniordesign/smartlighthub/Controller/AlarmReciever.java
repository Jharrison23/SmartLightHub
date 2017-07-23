package com.example.seniordesign.smartlighthub.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jamesharrison on 7/18/17.
 */

public class AlarmReciever extends BroadcastReceiver {



    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid());

    private DatabaseReference lightsRef = userRef.child("Lights");


    @Override
    public void onReceive(Context context, Intent intent) {


        Bundle extras = intent.getExtras();

        if (extras != null)
        {

            String light1Name = extras.getString("light1Name");
            String light1Color = extras.getString("light1Color");
            Boolean light1State = extras.getBoolean("light1State");
            
            Log.d("Alarm PullLight 1", "Name " + light1Name + " Color " + light1Color + " State " + light1State);
            

            DatabaseReference light1key = lightsRef.child("Light 1");

            light1key.child("Name").setValue(light1Name);
            light1key.child("Color").setValue(light1Color);
            light1key.child("State").setValue(light1State);


            String light2Name = extras.getString("light2Name");
            String light2Color = extras.getString("light2Color");
            Boolean light2State = extras.getBoolean("light2State");

            Log.d("Alarm PullLight 2", "Name " + light2Name + " Color " + light2Color + " State " + light2State);
            

            DatabaseReference light2key = lightsRef.child("Light 2");

            light2key.child("Name").setValue(light2Name);
            light2key.child("Color").setValue(light2Color);
            light2key.child("State").setValue(light2State);

            String light3Name = extras.getString("light3Name");
            String light3Color = extras.getString("light3Color");
            Boolean light3State = extras.getBoolean("light3State");

            Log.d("Alarm PullLight 3", "Name " + light3Name + " Color " + light3Color + " State " + light3State);

            DatabaseReference light3key = lightsRef.child("Light 3");

            light3key.child("Name").setValue(light3Name);
            light3key.child("Color").setValue(light3Color);
            light3key.child("State").setValue(light3State);

        }



        Toast.makeText(context, "Alarm went off", Toast.LENGTH_SHORT).show();
        Log.d("Alarm went off","time");

    }
}
