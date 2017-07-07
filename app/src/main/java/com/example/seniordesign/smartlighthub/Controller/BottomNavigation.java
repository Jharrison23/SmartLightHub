package com.example.seniordesign.smartlighthub.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.seniordesign.smartlighthub.R;
import com.example.seniordesign.smartlighthub.View.HomePage;
import com.example.seniordesign.smartlighthub.View.LightInfo;
import com.example.seniordesign.smartlighthub.View.LightPresets;
import com.example.seniordesign.smartlighthub.View.MainActivity;
import com.example.seniordesign.smartlighthub.View.MoodCreation;
import com.example.seniordesign.smartlighthub.View.Routines;
import com.example.seniordesign.smartlighthub.View.SettingsPage;
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

import java.util.List;

public class BottomNavigation extends AppCompatActivity {

    private BottomNavigationView navigation;
    private FragmentManager fragmentManager;
    private Fragment fragment;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Lights");


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigationPresets:
                    fragment  = new LightPresets();
                    setSelected(0);
                    break;

                case R.id.navigationHome:
                    fragment = new HomePage();
                    setSelected(1);
                    break;

                case R.id.navigationRoutines:
                    fragment = new Routines();
                    setSelected(2);
                    break;
            }

            final FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.container, fragment).commit();

            return true;

        }

    };

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
             
            }
        };

        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        //navigation.inflateMenu(R.menu.navigation_menu);

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction initialTransaction = fragmentManager.beginTransaction();

            initialTransaction.replace(R.id.container, new HomePage()).commit();

            navigation.getMenu().getItem(1).setChecked(true);
            getSupportActionBar().setTitle("Home");

            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.settingsPage)
        {
            startActivity(new Intent(BottomNavigation.this, SettingsPage.class));
        }

        if (item.getItemId() == R.id.musicMode) {

            JSONObject musicModeObject = new JSONObject();


            try {
                musicModeObject.put("0", 0);
                musicModeObject.put("1", 0);
                musicModeObject.put("2", 0);
                musicModeObject.put("3", 0);
                musicModeObject.put("4", 0);
                musicModeObject.put("5", 1);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            pubnubConfig(musicModeObject);

        }


        return super.onOptionsItemSelected(item);
    }

    public void setSelected(int selected)
    {
        switch (selected)
        {
            case 0:
                navigation.getMenu().getItem(0).setChecked(true);
                getSupportActionBar().setTitle("Presets");
                break;

            case 1:
                navigation.getMenu().getItem(1).setChecked(true);
                getSupportActionBar().setTitle("Home");
                break;

            case 2:
                navigation.getMenu().getItem(2).setChecked(true);
                getSupportActionBar().setTitle("Routines");
        }

    }

    public void logout()
    {
        mAuth.signOut();
        Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
    }


    public void pubnubConfig(final JSONObject pubnubObjects)
    {
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey("sub-c-40e3d906-4ee7-11e7-bf50-02ee2ddab7fe");
        pnConfiguration.setPublishKey("pub-c-6528095d-bc26-4768-a903-ac0a85174f81");
        pnConfiguration.setSecure(false);

        PubNub pubnub = new PubNub(pnConfiguration);

                pubnub.publish().message(pubnubObjects).channel("Light_1")
                        .async(new PNCallback<PNPublishResult>() {
                            @Override
                            public void onResponse(PNPublishResult result, PNStatus status) {
                                Log.d("Music Mode ", "Light 1 publish: " + pubnubObjects);
                            }
                        });

                pubnub.publish().message(pubnubObjects).channel("Light_2")
                        .async(new PNCallback<PNPublishResult>() {
                            @Override
                            public void onResponse(PNPublishResult result, PNStatus status) {
                                Log.d("Music Mode ", "Light 2 publish: " + pubnubObjects);
                            }
                        });

                pubnub.publish().message(pubnubObjects).channel("Light_3")
                        .async(new PNCallback<PNPublishResult>() {
                            @Override
                            public void onResponse(PNPublishResult result, PNStatus status) {
                                Log.d("Music Mode ", "Light 3 publish: " + pubnubObjects);
                            }
                        });


    }






}
