package com.example.seniordesign.smartlighthub.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {


    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Lights");

    private RecyclerView lightsRecyclerView;

    private LightsAdapter lightsAdapter;

    private Button lightControls;

    private Button musicControls;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        lightsRecyclerView = (RecyclerView) findViewById(R.id.lightsRecyclerView);
        lightsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        lightsAdapter = new LightsAdapter(createLightList(), this);

        lightsRecyclerView.setHasFixedSize(true);
        lightsRecyclerView.setAdapter(lightsAdapter);


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

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You must log out of the application to go back", Toast.LENGTH_SHORT).show();
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

                    lightList.add(newLight);

                }

                lightsRecyclerView.setHasFixedSize(true);
                lightsRecyclerView.setAdapter(lightsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        userRef.addValueEventListener(lightEventListener);
        return lightList;
    }
}