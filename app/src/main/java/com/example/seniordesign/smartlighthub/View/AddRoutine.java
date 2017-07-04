package com.example.seniordesign.smartlighthub.View;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.example.seniordesign.smartlighthub.Model.Light;
import com.example.seniordesign.smartlighthub.R;
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

public class AddRoutine extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Lights");

    private ConstraintLayout firstLightConstraint;
    private ConstraintLayout secondLightConstraint;
    private ConstraintLayout thirdLightConstraint;

    private TextView firstLightName;
    private TextView secondLightName;
    private TextView thirdLightName;

    private Switch firstLightState;
    private Switch secondLightState;
    private Switch thirdLightState;

    private EditText routineName;

    private TimePicker routineTimePicker;

    private LinearLayout weekdaysLayout;

    private ToggleButton sundayToggle;
    private ToggleButton mondayToggle;
    private ToggleButton tuesdayToggle;
    private ToggleButton wednesdayToggle;
    private ToggleButton thursdayToggle;
    private ToggleButton fridayToggle;
    private ToggleButton saturdayToggle;

    private Button addRoutineDone;
    private Button addRoutineNext;

    private List<Light> lightsList;

    private boolean constraintSet;

    private int pageNumber = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routine);



        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };


        constraintSet = false;

        firstLightConstraint = (ConstraintLayout) findViewById(R.id.firstLightContraint);
        secondLightConstraint = (ConstraintLayout) findViewById(R.id.secondLightConstraint);
        thirdLightConstraint = (ConstraintLayout) findViewById(R.id.thirdLightContraint);

        firstLightName = (TextView) findViewById(R.id.addRoutineFirstName) ;
        secondLightName = (TextView) findViewById(R.id.addRoutineSecondName);
        thirdLightName = (TextView) findViewById(R.id.addRoutineThirdName);

        firstLightState = (Switch) findViewById(R.id.addRoutineFirstSwitch);
        secondLightState = (Switch) findViewById(R.id.addRoutineSecondSwitch);
        thirdLightState = (Switch) findViewById(R.id.addRoutineThirdSwitch);

        sundayToggle = (ToggleButton) findViewById(R.id.sundayToggle);
        mondayToggle = (ToggleButton) findViewById(R.id.mondayToggle);
        tuesdayToggle = (ToggleButton) findViewById(R.id.tuesdayToggle);
        wednesdayToggle = (ToggleButton) findViewById(R.id.wednesdayToggle);
        thursdayToggle = (ToggleButton) findViewById(R.id.thursdayToggle);
        fridayToggle = (ToggleButton) findViewById(R.id.fridayToggle);
        saturdayToggle = (ToggleButton) findViewById(R.id.saturdayToggle);

        addRoutineNext = (Button) findViewById(R.id.routineNextButton);
        addRoutineNext.setOnClickListener(this);

        weekdaysLayout = (LinearLayout) findViewById(R.id.weekContainer);

        addRoutineDone = (Button) findViewById(R.id.routineDoneButton);
        addRoutineDone.setOnClickListener(this);

        routineTimePicker = (TimePicker) findViewById(R.id.routineTime);

        lightsList = new ArrayList<>();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child: children) {

                    Light light = child.getValue(Light.class);

                    lightsList.add(light);

                }
                if (!constraintSet) {

                    firstLightConstraint.setBackgroundColor(lightsList.get(0).getConvertedColor());
                    firstLightName.setText(lightsList.get(0).getName());
                    firstLightState.setChecked(lightsList.get(0).isState());

                    secondLightConstraint.setBackgroundColor(lightsList.get(1).getConvertedColor());
                    secondLightName.setText(lightsList.get(1).getName());
                    secondLightState.setChecked(lightsList.get(1).isState());

                    thirdLightConstraint.setBackgroundColor(lightsList.get(2).getConvertedColor());
                    thirdLightName.setText(lightsList.get(2).getName());
                    thirdLightState.setChecked(lightsList.get(2).isState());

                    constraintSet = true;
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.routineNextButton:

                pageNumber = 2;

                firstLightConstraint.setVisibility(View.VISIBLE);
                secondLightConstraint.setVisibility(View.VISIBLE);
                thirdLightConstraint.setVisibility(View.VISIBLE);

                routineTimePicker.setVisibility(View.INVISIBLE);
                weekdaysLayout.setVisibility(View.INVISIBLE);

                addRoutineNext.setVisibility(View.INVISIBLE);
                addRoutineDone.setVisibility(View.VISIBLE);


                break;

            case R.id.routineDoneButton:

                break;
        }
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
            pageNumber = 1;

            firstLightConstraint.setVisibility(View.INVISIBLE);
            secondLightConstraint.setVisibility(View.INVISIBLE);
            thirdLightConstraint.setVisibility(View.INVISIBLE);

            routineTimePicker.setVisibility(View.VISIBLE);
            weekdaysLayout.setVisibility(View.VISIBLE);

            addRoutineNext.setVisibility(View.VISIBLE);
            addRoutineDone.setVisibility(View.INVISIBLE);



        }


    }
}
