package com.example.seniordesign.smartlighthub;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yuku.ambilwarna.AmbilWarnaDialog;


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

    private int defaultColor;

    private Drawable lightDrawableColor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_info);

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

                // Color picker 2
                openColorPickerDialog(false);


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

                    lightDrawableColor = (Drawable) lightColor.getBackground();

                    int colorInt = ((ColorDrawable) lightDrawableColor).getColor();

                    int red = Color.red(colorInt);                                                                                                                                                        startActivity(getIntent());
                    int green = Color.green(colorInt);
                    int blue = Color.blue(colorInt);

                    String RGBcolor = red + ", " + green + ", " + blue;

                    key.child("Color").setValue(RGBcolor);

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

                    lightColor.setBackgroundColor(light.getConvertedColor());

                    defaultColor = light.getConvertedColor();

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


    // Color picker 2
    private void openColorPickerDialog(boolean AlphaSupport) {

        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(LightInfo.this, defaultColor, AlphaSupport, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int color) {

                defaultColor = color;

                lightColor.setBackgroundColor(color);



            }

            @Override
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {

                Toast.makeText(LightInfo.this, "Color Picker Closed", Toast.LENGTH_SHORT).show();
            }
        });
        ambilWarnaDialog.show();

    }



}