package com.example.seniordesign.smartlighthub;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seniordesign.smartlighthub.models.Light;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class LightInfo extends AppCompatActivity {


    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Lights");

    private List<Light> lightList;

    private TextView lightName;

    private ImageView lightColor;

    private Switch lightState;

    private int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_info);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            position = extras.getInt("pos");

            Toast.makeText(this, "position is " + position, Toast.LENGTH_SHORT).show();
        }


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    //Toast.makeText(LightInfo.this, "User logged in", Toast.LENGTH_SHORT).show();
                }
            }
        };

        lightList = new ArrayList<>();


        Toast.makeText(this, "before " + lightList.size(), Toast.LENGTH_SHORT).show();



        createLightList();

//
//        if (lightList.size() != 0)
//        {
//            Light light = lightList.get(position);
//
//            String name = light.getName();
//
//            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
//        }
//

        Toast.makeText(this, "after " + lightList.size(), Toast.LENGTH_SHORT).show();

        lightName = (TextView) findViewById(R.id.lightName);

        lightName.setText("wowo");

        lightColor = (ImageView) findViewById(R.id.lightColor);

        lightState = (Switch) findViewById(R.id.lightState);




    }


    @Override
    protected void onStart() {

        mAuth.addAuthStateListener(mAuthListener);


        super.onStart();
    }


    public void createLightList()
    {

        ValueEventListener lightEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {

                    Light newLight = child.getValue(Light.class);

                    lightList.add(newLight);
                }



        if (lightList.size() != 0)
        {
            Light light = lightList.get(position);

            lightName.setText(light.getName());

            lightColor.setBackgroundColor(Color.parseColor(light.getColor()));

            lightState.setChecked(light.isState());
            
        }







            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        userRef.addValueEventListener(lightEventListener);

    }

}