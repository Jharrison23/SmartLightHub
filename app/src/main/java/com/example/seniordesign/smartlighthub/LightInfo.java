package com.example.seniordesign.smartlighthub;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class LightInfo extends AppCompatActivity {


    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Lights");

    private DatabaseReference lightRef;
    private List<Light> lightList;

    private EditText lightName;

    private ImageView lightColor;

    private Switch lightState;

    private int position;

    private Button updateButton;

    private String lightNameString = "";

    private EditText lightColorText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_info);

        final ColorPicker colorPicker = new ColorPicker(LightInfo.this, 0, 0, 0);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            position = extras.getInt("pos");
        }


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Log.w("LightInfo", "User Logged in " + firebaseAuth.getCurrentUser());
                }
            }
        };

        lightList = new ArrayList<>();

        createLightList();


        lightName = (EditText) findViewById(R.id.lightName);
        lightColor = (ImageView) findViewById(R.id.lightColor);
        lightColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPicker.show();

                colorPicker.setCallback(new ColorPickerCallback() {
                    @Override
                    public void onColorChosen(@ColorInt int color) {
                        Toast.makeText(LightInfo.this, color + "", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        lightState = (Switch) findViewById(R.id.lightState);
        lightColorText = (EditText) findViewById(R.id.lightColorText);


        updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lightNameString != "")
                {
                    DatabaseReference key = userRef.child("Light " + (position + 1));

                    key.child("Name").setValue(lightName.getText().toString());
//                    key.child("Color").setValue(lightColor.getBackground().toString());


                    key.child("Color").setValue(lightColorText.getText().toString());
                    lightColor.setBackgroundColor(Color.parseColor(lightColorText.getText().toString()));

                    key.child("State").setValue(lightState.isChecked());

                            finish();
                            startActivity(getIntent());


                }

                else
                {
                    Toast.makeText(LightInfo.this, "No name", Toast.LENGTH_SHORT).show();
                }

            }
        });


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

                    lightNameString = light.getName();

                    lightColor.setBackgroundColor(Color.parseColor(light.getColor()));
                    lightColorText.setText(light.getColor());

                    lightState.setChecked(light.isState());

                    lightRef = userRef.child(light.getName());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        userRef.addValueEventListener(lightEventListener);

    }


    @Override
    public void onBackPressed() {
        Intent backHome = new Intent(LightInfo.this, HomePage.class);
        startActivity(backHome);
    }
}