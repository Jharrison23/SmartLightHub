package com.example.seniordesign.smartlighthub.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import com.example.seniordesign.smartlighthub.View.SettingsPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

                case R.id.navigationSettings:
                    fragment = new HomePage();
                    setSelected(1);
                    Toast.makeText(BottomNavigation.this, "Dont Click me retard", Toast.LENGTH_SHORT).show();
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
                getSupportActionBar().setTitle("Settings");
        }

    }

    public void logout()
    {
        mAuth.signOut();
        Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
    }

}
