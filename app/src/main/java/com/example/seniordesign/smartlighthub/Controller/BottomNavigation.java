package com.example.seniordesign.smartlighthub.Controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.seniordesign.smartlighthub.R;
import com.example.seniordesign.smartlighthub.View.HomePage;
import com.example.seniordesign.smartlighthub.View.LightInfo;
import com.example.seniordesign.smartlighthub.View.LightPresets;
import com.example.seniordesign.smartlighthub.View.SettingsPage;

public class BottomNavigation extends AppCompatActivity {

    private BottomNavigationView navigation;
    private FragmentManager fragmentManager;
    private Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigationPresets:
                    fragment  = new LightPresets();
                    getSupportActionBar().setTitle("Presets");
                    break;

                case R.id.navigationHome:
                    fragment = new HomePage();
                    getSupportActionBar().setTitle("Home");
                    break;

                case R.id.navigationSettings:
                    fragment = new SettingsPage();
                    getSupportActionBar().setTitle("Settings");
                    break;
            }

            final FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.container, fragment).commit();

            return true;

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);



        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        //navigation.inflateMenu(R.menu.navigation_menu);

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction initialTransaction = fragmentManager.beginTransaction();

        initialTransaction.replace(R.id.container, new HomePage()).commit();

        navigation.getMenu().getItem(1).setChecked(true);
        getSupportActionBar().setTitle("Home");

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

}
