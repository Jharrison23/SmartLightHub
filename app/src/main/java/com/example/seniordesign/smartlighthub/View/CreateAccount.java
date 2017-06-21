package com.example.seniordesign.smartlighthub.View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.seniordesign.smartlighthub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends Activity implements View.OnClickListener{

    private static final String TAG = "CreateAccount";

    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog progressDialog;

    private Button doneButton;
    private Button nextButton;
    private Button backButton;

    private EditText fullNameField;
    private EditText userNameField;
    private EditText emailField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private EditText firstLightField;
    private EditText secondLightField;
    private EditText thirdLightField;
    private EditText firstLightLabel;
    private EditText secondLightLabel;
    private EditText thirdLightLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        progressDialog = new ProgressDialog(this);

        getActionBar().hide();

        init();
    }

    public void init()
    {

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null)
                {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                }

                else
                {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        doneButton = (Button) findViewById(R.id.signUpButton);
        doneButton.setVisibility(View.INVISIBLE);
        doneButton.setOnClickListener(this);

        nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);

//        backButton = (Button) findViewById(R.id.backButton);
//        backButton.setVisibility(View.INVISIBLE);
//        backButton.setOnClickListener(this);

        fullNameField = (EditText) findViewById(R.id.fullNameField);
        userNameField = (EditText) findViewById(R.id.userNameField);
        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        confirmPasswordField = (EditText) findViewById(R.id.confirmPasswordField);

        firstLightField = (EditText) findViewById(R.id.light1Name);
        firstLightField.setVisibility(View.INVISIBLE);

        secondLightField = (EditText) findViewById(R.id.light2Name);
        secondLightField.setVisibility(View.INVISIBLE);

        thirdLightField = (EditText) findViewById(R.id.light3Name);
        thirdLightField.setVisibility(View.INVISIBLE);

        firstLightLabel = (EditText) findViewById(R.id.light1Label);
        firstLightLabel.setVisibility(View.INVISIBLE);

        secondLightLabel = (EditText) findViewById(R.id.light2Label);
        secondLightLabel.setVisibility(View.INVISIBLE);

        thirdLightLabel = (EditText) findViewById(R.id.light3Label);
        thirdLightLabel.setVisibility(View.INVISIBLE);



    }

    @Override
    public void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (mAuthListener != null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    public void registerUser()
    {
        final String nameString = fullNameField.getText().toString().trim();
        final String userNameString = userNameField.getText().toString().trim();
        final String emailString = emailField.getText().toString().trim();
        final String firstLightString = firstLightField.getText().toString().trim();
        final String secondLightString = secondLightField.getText().toString().trim();
        final String thirdLightString = thirdLightField.getText().toString().trim();

        String pass = passwordField.getText().toString().trim();
        String confirmPassString = confirmPasswordField.getText().toString().trim();


        if (!isEmpty(firstLightField) && !isEmpty(secondLightField) && !isEmpty(thirdLightField))
        {

            progressDialog.setMessage("Registering in please wait....");
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(emailString, pass).addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        String userID = mAuth.getCurrentUser().getUid();

                        DatabaseReference currentUserDB = mDatabase.child(userID);

                        currentUserDB.child("Name").setValue(nameString);
                        currentUserDB.child("Username").setValue(userNameString);
                        currentUserDB.child("Email").setValue(emailString);

                        DatabaseReference lightDB = currentUserDB.child("Lights");

                        DatabaseReference firstLightDB = lightDB.child("Light 1");
                        firstLightDB.child("Name").setValue(firstLightString);
                        firstLightDB.child("Color").setValue("255, 255, 255");
                        firstLightDB.child("State").setValue(true);


                        DatabaseReference secondLightDB = lightDB.child("Light 2");
                        secondLightDB.child("Name").setValue(secondLightString);
                        secondLightDB.child("Color").setValue("255, 255, 255");
                        secondLightDB.child("State").setValue(true);


                        DatabaseReference thirdLightDB = lightDB.child("Light 3");
                        thirdLightDB.child("Name").setValue(thirdLightString);
                        thirdLightDB.child("Color").setValue("255, 255, 255");
                        thirdLightDB.child("State").setValue(false);



                        Log.d(TAG, "Create user successful");
                        Toast.makeText(CreateAccount.this, "User Successfully Created", Toast.LENGTH_SHORT).show();

                        progressDialog.cancel();

                        Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                        startActivity(intent);
                    }

                    else
                    {
                        Log.w(TAG, "Create user : Not Successful ");
                        Toast.makeText(CreateAccount.this, "User not created", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();

                    }
                }
            });

        }

        else
        {
            Toast.makeText(CreateAccount.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.nextButton:

                if (!isEmpty(fullNameField) && !isEmpty(userNameField) && !isEmpty(emailField)
                        && !isEmpty(passwordField) && !isEmpty(confirmPasswordField))
                {
                    if (passwordField.getText().toString().equals(confirmPasswordField.getText().toString()))
                    {
                        firstLightField.setVisibility(View.VISIBLE);
                        secondLightField.setVisibility(View.VISIBLE);
                        thirdLightField.setVisibility(View.VISIBLE);

                        firstLightLabel.setVisibility(View.VISIBLE);
                        secondLightLabel.setVisibility(View.VISIBLE);
                        thirdLightLabel.setVisibility(View.VISIBLE);


                        //backButton.setVisibility(View.VISIBLE);
                        doneButton.setVisibility(View.VISIBLE);

                        fullNameField.setVisibility(View.INVISIBLE);
                        userNameField.setVisibility(View.INVISIBLE);
                        emailField.setVisibility(View.INVISIBLE);
                        passwordField.setVisibility(View.INVISIBLE);
                        confirmPasswordField.setVisibility(View.INVISIBLE);
                        nextButton.setVisibility(View.INVISIBLE);
                    }

                    else
                    {
                        Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }


                }

                else
                {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }


                break;

//            case R.id.backButton:
//
//
//                firstLightField.setVisibility(View.INVISIBLE);
//                secondLightField.setVisibility(View.INVISIBLE);
//                thirdLightField.setVisibility(View.INVISIBLE);
//
//                firstLightLabel.setVisibility(View.INVISIBLE);
//                secondLightLabel.setVisibility(View.INVISIBLE);
//                thirdLightLabel.setVisibility(View.INVISIBLE);
//
//
//                backButton.setVisibility(View.INVISIBLE);
//                doneButton.setVisibility(View.INVISIBLE);
//
//                fullNameField.setVisibility(View.VISIBLE);
//                userNameField.setVisibility(View.VISIBLE);
//                emailField.setVisibility(View.VISIBLE);
//                passwordField.setVisibility(View.VISIBLE);
//                confirmPasswordField.setVisibility(View.VISIBLE);
//                nextButton.setVisibility(View.VISIBLE);
//
//
//                break;

            case R.id.signUpButton:
                registerUser();
                break;
        }
    }


    public boolean isEmpty(EditText field)
    {
        return field.getText().toString().trim().equals("");
    }






}
