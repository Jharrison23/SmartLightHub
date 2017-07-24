package com.example.seniordesign.smartlighthub.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.seniordesign.smartlighthub.Model.Light;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamesharrison on 7/18/17.
 */

public class AlarmReciever extends BroadcastReceiver {



    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid());

    private DatabaseReference lightsRef = userRef.child("Lights");

    private List<Light> lightsList;
    
    private List<JSONObject> pubnubObjects;
    
    

    @Override
    public void onReceive(Context context, Intent intent) {

        lightsList = new ArrayList<>();

        pubnubObjects = new ArrayList<>();
        Bundle extras = intent.getExtras();

        if (extras != null)
        {

            String light1Name = extras.getString("light1Name");
            String light1Color = extras.getString("light1Color");
            Boolean light1State = extras.getBoolean("light1State");
            
            Light light1 = new Light(light1Name, light1Color, light1State);
            
            lightsList.add(light1);
            
            Log.d("Alarm PullLight 1", "Name " + light1Name + " Color " + light1Color + " State " + light1State);
            

            DatabaseReference light1key = lightsRef.child("Light 1");

            light1key.child("Name").setValue(light1Name);
            light1key.child("Color").setValue(light1Color);
            light1key.child("State").setValue(light1State);


            String light2Name = extras.getString("light2Name");
            String light2Color = extras.getString("light2Color");
            Boolean light2State = extras.getBoolean("light2State");

            Light light2 = new Light(light2Name, light2Color, light2State);

            lightsList.add(light2);
            
            Log.d("Alarm PullLight 2", "Name " + light2Name + " Color " + light2Color + " State " + light2State);
            

            DatabaseReference light2key = lightsRef.child("Light 2");

            light2key.child("Name").setValue(light2Name);
            light2key.child("Color").setValue(light2Color);
            light2key.child("State").setValue(light2State);

            String light3Name = extras.getString("light3Name");
            String light3Color = extras.getString("light3Color");
            Boolean light3State = extras.getBoolean("light3State");

            Light light3 = new Light(light3Name, light3Color, light3State);

            lightsList.add(light3);
            
            Log.d("Alarm PullLight 3", "Name " + light3Name + " Color " + light3Color + " State " + light3State);

            DatabaseReference light3key = lightsRef.child("Light 3");

            light3key.child("Name").setValue(light3Name);
            light3key.child("Color").setValue(light3Color);
            light3key.child("State").setValue(light3State);
            
            for (int i = 0; i < lightsList.size(); i++) {
                JSONObject rbgObject = new JSONObject();


                try {
                    rbgObject.put("0", lightsList.get(i).getRed());
                    rbgObject.put("1", lightsList.get(i).getGreen());
                    rbgObject.put("2", lightsList.get(i).getBlue());
                    rbgObject.put("3", 0);

                    int stateFlag = 0;

                    if (lightsList.get(i).isState())
                    {
                        stateFlag = 1;
                    }

                    else if (!lightsList.get(i).isState()) {
                        stateFlag = 0;
                    }

                    rbgObject.put("4", stateFlag);

                    rbgObject.put("5", 0);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (pubnubObjects.size() < 3)
                {
                    pubnubObjects.add(rbgObject);
                }

            }

            pubnubConfig(pubnubObjects);

        }



        Toast.makeText(context, "Alarm Activated", Toast.LENGTH_SHORT).show();
        Log.d("Alarm went off","time");

    }


    public void pubnubConfig(final List<JSONObject> pubnubObjects)
    {
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey("sub-c-40e3d906-4ee7-11e7-bf50-02ee2ddab7fe");
        pnConfiguration.setPublishKey("pub-c-6528095d-bc26-4768-a903-ac0a85174f81");
        pnConfiguration.setSecure(false);

        PubNub pubnub = new PubNub(pnConfiguration);


        for (int i = 0; i < pubnubObjects.size(); i++)
        {

            switch(i)
            {
                case 0:
                    final JSONObject light1Message = pubnubObjects.get(0);
                    pubnub.publish().message(light1Message).channel("Light_1")
                            .async(new PNCallback<PNPublishResult>() {
                                @Override
                                public void onResponse(PNPublishResult result, PNStatus status) {
                                    Log.d("HomePage", "Light 1 publish: " + light1Message);
                                }
                            });

                    break;

                case 1:

                    final JSONObject light2Message = pubnubObjects.get(1);
                    pubnub.publish().message(light2Message).channel("Light_2")
                            .async(new PNCallback<PNPublishResult>() {
                                @Override
                                public void onResponse(PNPublishResult result, PNStatus status) {
                                    Log.d("HomePage", "Light 2 publish: " + light2Message);
                                }
                            });

                    break;

                case 2:

                    final JSONObject light3Message = pubnubObjects.get(2);
                    pubnub.publish().message(light3Message).channel("Light_3")
                            .async(new PNCallback<PNPublishResult>() {
                                @Override
                                public void onResponse(PNPublishResult result, PNStatus status) {
                                    Log.d("HomePage", "Light 3 publish: " + light3Message);
                                }
                            });

                    break;

            }
        }

    }


}
