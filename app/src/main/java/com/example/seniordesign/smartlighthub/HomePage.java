package com.example.seniordesign.smartlighthub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;


    Button lightControls;

    Button musicControls;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null)
                {
                    Toast.makeText(HomePage.this, "Welcome", Toast.LENGTH_SHORT).show();
                }
            }
        };


        lightControls = (Button) findViewById(R.id.lightControls);
        lightControls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePage.this, LightControls.class);
                startActivity(intent);
            }
        });


        musicControls = (Button) findViewById(R.id.musicControls);
        musicControls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePage.this, MusicControls.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {

        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.settingsButton)
        {
            startActivity(new Intent(HomePage.this, SettingsPage.class));
        }

        if (item.getItemId() == R.id.logoutButton)
        {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout()
    {
        mAuth.signOut();
        Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(HomePage.this, MainActivity.class));
    }

}
