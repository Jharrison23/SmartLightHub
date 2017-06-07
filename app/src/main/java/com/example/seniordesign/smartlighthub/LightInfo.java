package com.example.seniordesign.smartlighthub;

import android.graphics.Color;
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

    private boolean dataLoaded = false;

    private Button editButton;

    private Button saveButton;

    private Button cancelButton;

    private String lightNameString = "";

    private EditText lightColorText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_info);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            position = extras.getInt("pos");

//            Toast.makeText(this, "position is " + position, Toast.LENGTH_SHORT).show();
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


//        Toast.makeText(this, "before " + lightList.size(), Toast.LENGTH_SHORT).show();



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

//        Toast.makeText(this, "after " + lightList.size(), Toast.LENGTH_SHORT).show();

        lightName = (EditText) findViewById(R.id.lightName);

        while(dataLoaded)
        {


            lightState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                    {
                        Toast.makeText(LightInfo.this, "checked", Toast.LENGTH_SHORT).show();
                    }
                }
            });


//            lightName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    lightName.addTextChangedListener(new TextWatcher() {
//                        @Override
//                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                        }
//
//                        @Override
//                        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                        }
//
//                        @Override
//                        public void afterTextChanged(Editable s) {
//
//                            lightRef.child("Name").setValue(lightName.getText().toString());
//                        }
//                    });
//                }
//            });


        }

        lightColor = (ImageView) findViewById(R.id.lightColor);
        lightState = (Switch) findViewById(R.id.lightState);
        lightColorText = (EditText) findViewById(R.id.lightColorText);


        editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton.setVisibility(View.VISIBLE);
                saveButton.setClickable(true);
                cancelButton.setVisibility(View.VISIBLE);
                cancelButton.setClickable(true);
                editButton.setVisibility(View.INVISIBLE);
                editButton.setClickable(false);




            }
        });

        saveButton = (Button) findViewById(R.id.saveButton);



        saveButton.setVisibility(View.INVISIBLE);
        saveButton.setClickable(false);


        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton.setVisibility(View.INVISIBLE);
                saveButton.setClickable(false);
                cancelButton.setVisibility(View.INVISIBLE);
                cancelButton.setClickable(false);
                editButton.setVisibility(View.VISIBLE);
                editButton.setClickable(true);

            }
        });
        cancelButton.setVisibility(View.INVISIBLE);
        cancelButton.setClickable(false);





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



//                    DatabaseReference key = userRef.child(light.getName());
//
//                    Toast.makeText(LightInfo.this, key.toString(), Toast.LENGTH_SHORT).show();
////
//                    key.child("Name").setValue(lightName.getText().toString());
//                    key.child("Color").setValue(lightColor.getBackground().toString());
//                    key.child("State").setValue(lightState.isChecked());

                    dataLoaded = true;





                }


                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        saveButton.setVisibility(View.INVISIBLE);
                        saveButton.setClickable(false);
                        cancelButton.setVisibility(View.INVISIBLE);
                        cancelButton.setClickable(false);
                        editButton.setVisibility(View.VISIBLE);
                        editButton.setClickable(true);


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
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        userRef.addValueEventListener(lightEventListener);

    }

}