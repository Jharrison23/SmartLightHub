package com.example.seniordesign.smartlighthub.View;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.seniordesign.smartlighthub.Controller.LightsAdapter;
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
import java.util.Arrays;
import java.util.List;

public class HomePage extends Fragment {


    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Lights");

    private RecyclerView lightsRecyclerView;

    private LightsAdapter lightsAdapter;

    private Button lightControls;

    private Button musicControls;

    private String publishLight1;

    private List<JSONObject> pubnubObjects;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.activity_home_page, container, false);

        pubnubObjects = new ArrayList<>();

        lightsRecyclerView = (RecyclerView) view.findViewById(R.id.lightsRecyclerView);
        lightsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        lightsAdapter = new LightsAdapter(createLightList(), getContext());
//
//        lightsRecyclerView.setHasFixedSize(true);
//        lightsRecyclerView.setAdapter(lightsAdapter);


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


//        lightControls = (Button) findViewById(R.id.lightControls);
//        lightControls.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(HomePage.this, LightControls.class);
//                startActivity(intent);
//            }
//        });
//
//
//        musicControls = (Button) findViewById(R.id.musicControls);
//        musicControls.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(HomePage.this, MusicControls.class);
//                startActivity(intent);
//            }
//        });

        return view;

    }

    @Override
    public void onStart() {

        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if(item.getItemId() == R.id.settingsButton)
//        {
//            startActivity(new Intent(getContext(), LightControls.class));
//        }
//
////        if (item.getItemId() == R.id.logoutButton)
////        {
////            logout();
////        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public void logout()
    {
        mAuth.signOut();
        Toast.makeText(getContext(), "User Logged Out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getContext(), MainActivity.class));
    }

//    @Override
//    public void onBackPressed()
//    {
//        Toast.makeText(getContext(), "You must log out of the application to go back", Toast.LENGTH_SHORT).show();
//    }

    public List<Light> createLightList()
    {


        final List<Light> lightList = new ArrayList<>();

        ValueEventListener lightEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {

                    Light newLight = child.getValue(Light.class);

                    lightList.add(newLight);

                    JSONObject rbgObject = new JSONObject();


                    try {
                        rbgObject.put("0", newLight.getRed());
                        rbgObject.put("1", newLight.getGreen());
                        rbgObject.put("2", newLight.getBlue());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    pubnubObjects.add(rbgObject);

                }

                lightsRecyclerView.setHasFixedSize(true);
                lightsRecyclerView.setAdapter(lightsAdapter);


                pubnubConfig(pubnubObjects);

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
