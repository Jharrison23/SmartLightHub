package com.example.seniordesign.smartlighthub.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.seniordesign.smartlighthub.R;
import com.example.seniordesign.smartlighthub.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsPage extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SettingsPage";

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid());

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

    @Override
    public void onStart() {
        super.onStart();
        showData();

    }

    public void init()
    {


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
                updateInfo();
                notEditable();
                break;

            case R.id.editButton:
                isEditable();
                break;

            case R.id.cancelButton:
                showData();
                notEditable();
                break;
        }
    }

    public void showData()
    {
        ValueEventListener userEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                fullnameField.setText(user.Name);
                emailField.setText(user.Email);
                userNameField.setText(user.Username);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed
                Log.w(TAG, "Loading User data failed :onCancelled", databaseError.toException());

            }
        };

        userRef.addValueEventListener(userEventListener);
    }


    public void updateInfo()
    {

        userRef.child("Name").setValue(fullnameField.getText().toString());
        userRef.child("Email").setValue(emailField.getText().toString());
        userRef.child("Username").setValue(userNameField.getText().toString());

    }





}