package com.example.seniordesign.smartlighthub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class SettingsPage extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SettingsPage";

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid());

    private EditText fullnameField;
    private EditText userNameField;
    private EditText emailField;
    private EditText passwordField;

    Button saveButton;
    Button cancelButton;
    Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

       init();
    }

    public void init()
    {

        showData();

        fullnameField = (EditText) findViewById(R.id.fullNameField);
        userNameField = (EditText) findViewById(R.id.userNameField);
        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);


        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this);

        editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(this);

        notEditable();

//
//        userRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Map<String, Object> userData = (Map<String, Object>) dataSnapshot.getValue();
//
//                fullnameField.setText(userData.get("Name").toString());
//                emailField.setText(userData.get("Email").toString());
//                userNameField.setText(userData.get("Username").toString());
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//                // Getting Post failed
//                Log.w(TAG, "Loading User data failed :onCancelled", databaseError.toException());
//            }
//        });


    }


    public void notEditable()
    {


        editButton.setVisibility(View.VISIBLE);

        saveButton.setVisibility(View.INVISIBLE);

        cancelButton.setVisibility(View.INVISIBLE);


        fullnameField.setClickable(false);
        fullnameField.setFocusable(false);
        userNameField.setClickable(false);
        userNameField.setFocusable(false);
        emailField.setClickable(false);
        emailField.setFocusable(false);
        passwordField.setClickable(false);
        passwordField.setFocusable(false);
    }

    public void isEditable()
    {
        editButton.setVisibility(View.INVISIBLE);

        saveButton.setVisibility(View.VISIBLE);

        cancelButton.setVisibility(View.VISIBLE);


        fullnameField.setClickable(true);
        fullnameField.setFocusableInTouchMode(true);
        userNameField.setClickable(true);
        userNameField.setFocusableInTouchMode(true);
        emailField.setClickable(true);
        emailField.setFocusableInTouchMode(true);
        passwordField.setClickable(true);
        passwordField.setFocusableInTouchMode(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.saveButton:
                notEditable();
                break;

            case R.id.editButton:
                isEditable();
                break;

            case R.id.cancelButton:
                notEditable();
                break;
        }
    }

    public void showData()
    {
        ValueEventListener userEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> userData = (Map<String, Object>) dataSnapshot.getValue();

                fullnameField.setText(userData.get("Name").toString());
                emailField.setText(userData.get("Email").toString());
                userNameField.setText(userData.get("Username").toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        userRef.addValueEventListener(userEventListener);
    }
}
