package com.example.seniordesign.smartlighthub.View;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seniordesign.smartlighthub.Controller.BottomNavigation;
import com.example.seniordesign.smartlighthub.Model.Light;
import com.example.seniordesign.smartlighthub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;


public class AddPresetsActivity extends AppCompatActivity implements View.OnClickListener{
    
    private EditText firstLightName;
    private ImageView firstLightColor;
    private Switch firstLightState;
    private Button firstPageNext;

    private EditText secondLightName;
    private ImageView secondLightColor;
    private Switch secondLightState;
    private Button secondPageNext;

    private EditText thirdLightName;
    private ImageView thirdLightColor;
    private Switch thirdLightState;
    private Button thirdPageDone;

    private TextView lightNumber;

    private int pageNumber;
    private int defaultColor;

    private String presetName;

    private List<Light> homepageLightList;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference presetsRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Presets");

    private DatabaseReference lightsRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Lights");





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_presets);


        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Create Preset");

        }
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };


        Bundle extras = getIntent().getExtras();

        if (extras != null)
        {
            presetName = extras.getString("Preset Name");

        }
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onBackPressed() {

        if (pageNumber == 1) {
            super.onBackPressed();
        }

        else if (pageNumber == 2) {
            lightNumber.setText("Light 1");


            firstLightName.setVisibility(View.VISIBLE);
            firstLightColor.setVisibility(View.VISIBLE);
            firstLightState.setVisibility(View.VISIBLE);
            firstPageNext.setVisibility(View.VISIBLE);

            secondLightName.setVisibility(View.INVISIBLE);
            secondLightColor.setVisibility(View.INVISIBLE);
            secondLightState.setVisibility(View.INVISIBLE);
            secondPageNext.setVisibility(View.INVISIBLE);

            thirdLightName.setVisibility(View.INVISIBLE);
            thirdLightColor.setVisibility(View.INVISIBLE);
            thirdLightState.setVisibility(View.INVISIBLE);
            thirdPageDone.setVisibility(View.INVISIBLE);

            pageNumber = 1;
        }

        else if (pageNumber == 3) {

            lightNumber.setText("Light 2");

            firstLightName.setVisibility(View.INVISIBLE);
            firstLightColor.setVisibility(View.INVISIBLE);
            firstLightState.setVisibility(View.INVISIBLE);
            firstPageNext.setVisibility(View.INVISIBLE);

            secondLightName.setVisibility(View.VISIBLE);
            secondLightColor.setVisibility(View.VISIBLE);
            secondLightState.setVisibility(View.VISIBLE);
            secondPageNext.setVisibility(View.VISIBLE);

            thirdLightName.setVisibility(View.INVISIBLE);
            thirdLightColor.setVisibility(View.INVISIBLE);
            thirdLightState.setVisibility(View.INVISIBLE);
            thirdPageDone.setVisibility(View.INVISIBLE);

            pageNumber = 2;

        }

    }

    public void init() {

        homepageLightList = new ArrayList<>();
        
        firstLightName  = (EditText) findViewById(R.id.firstLightName);
        firstLightColor = (ImageView) findViewById(R.id.firstLightColor_preset);
        firstLightColor.setOnClickListener(this);
        firstLightState = (Switch) findViewById(R.id.firstLightState);
        firstPageNext = (Button) findViewById(R.id.firstPageNext);
        firstPageNext.setOnClickListener(this);

        secondLightName  = (EditText) findViewById(R.id.secondLightName);
        secondLightColor = (ImageView) findViewById(R.id.secondLightColor_preset);
        secondLightColor.setOnClickListener(this);
        secondLightState = (Switch) findViewById(R.id.secondLightState);
        secondPageNext = (Button) findViewById(R.id.secondPageNext);
        secondPageNext.setOnClickListener(this);

        thirdLightName  = (EditText) findViewById(R.id.thirdLightName);
        thirdLightColor = (ImageView) findViewById(R.id.thirdLightColor_preset);
        thirdLightColor.setOnClickListener(this);
        thirdLightState = (Switch) findViewById(R.id.thirdLightState);
        thirdPageDone = (Button) findViewById(R.id.thirdPageDone);
        thirdPageDone.setOnClickListener(this);

        lightNumber = (TextView) findViewById(R.id.presetLightNumber);
        lightNumber.setText("Light 1");

        pageNumber = 1;



        lightsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child: children) {

                    Light currentLight = child.getValue(Light.class);
                    homepageLightList.add(currentLight);

                }

                firstLightName.setText(homepageLightList.get(0).getName());
                firstLightColor.setBackgroundColor(homepageLightList.get(0).getConvertedColor());

                secondLightName.setText(homepageLightList.get(1).getName());
                secondLightColor.setBackgroundColor(homepageLightList.get(1).getConvertedColor());

                thirdLightName.setText(homepageLightList.get(2).getName());
                thirdLightColor.setBackgroundColor(homepageLightList.get(2).getConvertedColor());



            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            
            case R.id.firstPageNext:
                lightNumber.setText("Light 2");


                firstLightName.setVisibility(View.INVISIBLE);
                firstLightColor.setVisibility(View.INVISIBLE);
                firstLightState.setVisibility(View.INVISIBLE);
                firstPageNext.setVisibility(View.INVISIBLE);

                secondLightName.setVisibility(View.VISIBLE);
                secondLightColor.setVisibility(View.VISIBLE);
                secondLightState.setVisibility(View.VISIBLE);
                secondPageNext.setVisibility(View.VISIBLE);

                thirdLightName.setVisibility(View.INVISIBLE);
                thirdLightColor.setVisibility(View.INVISIBLE);
                thirdLightState.setVisibility(View.INVISIBLE);
                thirdPageDone.setVisibility(View.INVISIBLE);

                pageNumber = 2;
                break;

            case R.id.secondPageNext:
                lightNumber.setText("Light 3");


                firstLightName.setVisibility(View.INVISIBLE);
                firstLightColor.setVisibility(View.INVISIBLE);
                firstLightState.setVisibility(View.INVISIBLE);
                firstPageNext.setVisibility(View.INVISIBLE);

                secondLightName.setVisibility(View.INVISIBLE);
                secondLightColor.setVisibility(View.INVISIBLE);
                secondLightState.setVisibility(View.INVISIBLE);
                secondPageNext.setVisibility(View.INVISIBLE);

                thirdLightName.setVisibility(View.VISIBLE);
                thirdLightColor.setVisibility(View.VISIBLE);
                thirdLightState.setVisibility(View.VISIBLE);
                thirdPageDone.setVisibility(View.VISIBLE);

                pageNumber = 3;

                break;

            case R.id.thirdPageDone:
                savePreset();
                startActivity(new Intent(AddPresetsActivity.this, BottomNavigation.class));
                break;

            case R.id.firstLightColor_preset:
                defaultColor = ((ColorDrawable) firstLightColor.getBackground()).getColor();
                openColorPickerDialog(false, firstLightColor);
                break;


            case R.id.secondLightColor_preset:
                defaultColor = ((ColorDrawable) secondLightColor.getBackground()).getColor();
                openColorPickerDialog(false, secondLightColor);
                break;

            case R.id.thirdLightColor_preset:

                defaultColor = ((ColorDrawable) thirdLightColor.getBackground()).getColor();

                openColorPickerDialog(false, thirdLightColor);
                break;
        }
        
    }


    // Color picker 2
    private void openColorPickerDialog(boolean AlphaSupport, final ImageView lightColor) {

        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(AddPresetsActivity.this, defaultColor, AlphaSupport, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int color) {

                defaultColor = color;

                lightColor.setBackgroundColor(color);


            }

            @Override
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {

                //Toast.makeText(AddPresetsActivity.this, "Color Picker Closed", Toast.LENGTH_SHORT).show();
            }
        });

        ambilWarnaDialog.show();

    }


    public void savePreset()
    {
        DatabaseReference currentPreset = presetsRef.child(presetName);

        currentPreset.child("Name").setValue(presetName);

        DatabaseReference lightRef = currentPreset.child("Lights");

        DatabaseReference firstLightPresetRef = lightRef.child("Light 1");
        firstLightPresetRef.child("Name").setValue(firstLightName.getText().toString());
        Drawable firstLightDrawableColor = (Drawable) firstLightColor.getBackground();

        int firstColorInt = ((ColorDrawable) firstLightDrawableColor).getColor();

        int firstRed = Color.red(firstColorInt);                                                                                                                                                        startActivity(getIntent());
        int firstGreen = Color.green(firstColorInt);
        int firstBlue = Color.blue(firstColorInt);

        String firstRGBcolor = firstRed + ", " + firstGreen + ", " + firstBlue;

        firstLightPresetRef.child("Color").setValue(firstRGBcolor);
        firstLightPresetRef.child("State").setValue(firstLightState.isChecked());



        DatabaseReference secondLightPresetRef = lightRef.child("Light 2");
        secondLightPresetRef.child("Name").setValue(secondLightName.getText().toString());
        Drawable secondLightDrawableColor = (Drawable) secondLightColor.getBackground();

        int secondColorInt = ((ColorDrawable) secondLightDrawableColor).getColor();

        int secondRed = Color.red(secondColorInt);                                                                                                                                                        startActivity(getIntent());
        int secondGreen = Color.green(secondColorInt);
        int secondBlue = Color.blue(secondColorInt);

        String secondRGBcolor = secondRed + ", " + secondGreen + ", " + secondBlue;


        secondLightPresetRef.child("Color").setValue(secondRGBcolor);        
        secondLightPresetRef.child("State").setValue(secondLightState.isChecked());



        DatabaseReference thirdLightPresetRef = lightRef.child("Light 3");
        thirdLightPresetRef.child("Name").setValue(thirdLightName.getText().toString());
        Drawable thirdLightDrawableColor = (Drawable) thirdLightColor.getBackground();

        int thirdColorInt = ((ColorDrawable) thirdLightDrawableColor).getColor();

        int thirdRed = Color.red(thirdColorInt);                                                                                                                                                        startActivity(getIntent());
        int thirdGreen = Color.green(thirdColorInt);
        int thirdBlue = Color.blue(thirdColorInt);

        String thirdRGBcolor = thirdRed + ", " + thirdGreen + ", " + thirdBlue;

        thirdLightPresetRef.child("Color").setValue(thirdRGBcolor);
        thirdLightPresetRef.child("State").setValue(thirdLightState.isChecked());





    }



}
