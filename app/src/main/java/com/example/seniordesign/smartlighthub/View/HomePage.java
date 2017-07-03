package com.example.seniordesign.smartlighthub.View;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.seniordesign.smartlighthub.Controller.HomePageAdapter;
import com.example.seniordesign.smartlighthub.R;
import com.example.seniordesign.smartlighthub.Model.Light;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends Fragment {


    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Lights");

    private RecyclerView lightsRecyclerView;

    private HomePageAdapter homePageAdapter;

    private Button lightControls;

    private Button musicControls;

    private String publishLight1;

    private List<JSONObject> pubnubObjects;

    private List<Light> listOfLights;

    private Boolean isPublished;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        View view;

        Bundle extras = this.getArguments();

        if (extras != null)
        {

            String light1Name = extras.getString("light1Name");
            String light1Color = extras.getString("light1Color");
            Boolean light1State = extras.getBoolean("light1State");


            DatabaseReference light1key = userRef.child("Light 1");

            light1key.child("Name").setValue(light1Name);
            light1key.child("Color").setValue(light1Color);
            light1key.child("State").setValue(light1State);




            String light2Name = extras.getString("light2Name");
            String light2Color = extras.getString("light2Color");
            Boolean light2State = extras.getBoolean("light2State");


            DatabaseReference light2key = userRef.child("Light 2");

            light2key.child("Name").setValue(light2Name);
            light2key.child("Color").setValue(light2Color);
            light2key.child("State").setValue(light2State);



            String light3Name = extras.getString("light3Name");
            String light3Color = extras.getString("light3Color");
            Boolean light3State = extras.getBoolean("light3State");

            DatabaseReference light3key = userRef.child("Light 3");

            light3key.child("Name").setValue(light3Name);
            light3key.child("Color").setValue(light3Color);
            light3key.child("State").setValue(light3State);



        }

        view = inflater.inflate(R.layout.activity_home_page, container, false);

        pubnubObjects = new ArrayList<>();

        listOfLights = new ArrayList<>();

        lightsRecyclerView = (RecyclerView) view.findViewById(R.id.lightsRecyclerView);
        lightsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        homePageAdapter = new HomePageAdapter(createLightList(), getContext());
//
//        lightsRecyclerView.setHasFixedSize(true);
//        lightsRecyclerView.setAdapter(homePageAdapter);


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null)
                {
                    Toast.makeText(getContext(), "Welcome", Toast.LENGTH_SHORT).show();
                }
            }
        };




        return view;

    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {

        super.onStart();


        mAuth.addAuthStateListener(mAuthListener);

        final List<Light> lightList = new ArrayList<>();

        isPublished = false;

        mAuth.addAuthStateListener(mAuthListener);

        ValueEventListener lightEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {

                    Light newLight = child.getValue(Light.class);

                    Log.d("LIGHT VALUE ", "i = " + child + " " + newLight);

                    lightList.add(newLight);

                    JSONObject rbgObject = new JSONObject();


                    try {
                        rbgObject.put("0", newLight.getRed());
                        rbgObject.put("1", newLight.getGreen());
                        rbgObject.put("2", newLight.getBlue());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (pubnubObjects.size() < 3)
                    {
                        pubnubObjects.add(rbgObject);
                    }

                }

                Log.d("OnResume PubObject = ","" + pubnubObjects.toString());

                if (!isPublished)
                {
                    pubnubConfig(pubnubObjects);

                    isPublished = true;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        userRef.addValueEventListener(lightEventListener);




    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d("OnPause PubObject = ","" + pubnubObjects.toString());

        pubnubObjects.clear();

        Log.d("PauseClear PubObject = ","" + pubnubObjects.toString());


    }

    public List<Light> createLightList()
    {


        final List<Light> lightList = new ArrayList<>();

        ValueEventListener lightEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {

                    Light newLight = child.getValue(Light.class);

                    Log.d("LIGHT VALUE ", "i = " + child + " " + newLight);

                    lightList.add(newLight);

                    JSONObject rbgObject = new JSONObject();


                    try {
                        rbgObject.put("0", newLight.getRed());
                        rbgObject.put("1", newLight.getGreen());
                        rbgObject.put("2", newLight.getBlue());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //pubnubObjects.add(rbgObject);

                }

                listOfLights = lightList;

                lightsRecyclerView.setHasFixedSize(true);
                lightsRecyclerView.setAdapter(homePageAdapter);


                //pubnubConfig(pubnubObjects);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        userRef.addValueEventListener(lightEventListener);
        return lightList;
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