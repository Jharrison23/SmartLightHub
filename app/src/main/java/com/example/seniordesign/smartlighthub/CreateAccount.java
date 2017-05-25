package com.example.seniordesign.smartlighthub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {

    private static final String TAG = "CreateAccount";

    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog progressDialog;

    private Button createAccount;

    private EditText fullName, userName, email, password, confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        progressDialog = new ProgressDialog(this);

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

        createAccount = (Button) findViewById(R.id.createAccountButton);

        fullName = (EditText) findViewById(R.id.fullNameField);
        userName = (EditText) findViewById(R.id.userNameField);
        email = (EditText) findViewById(R.id.emailField);
        password = (EditText) findViewById(R.id.passwordField);
        confirmPassword = (EditText) findViewById(R.id.confirmPasswordField);

//        createAccount.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                String emailAddress = email.getText().toString();
//
//                String pass = password.getText().toString();
//
//                if (!emailAddress.equals("") && !pass.equals(""))
//                {
//                    mAuth.createUserWithEmailAndPassword(emailAddress, pass);
//                    Toast.makeText(CreateAccount.this, "user Signed in", Toast.LENGTH_SHORT).show();
//                }
//
//                else
//                {
//                    Toast.makeText(CreateAccount.this, "Fill out email and password", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });



        createAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                registerUser();
            }
        });

    }

    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

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
        final String nameString = fullName.getText().toString().trim();
        final String userNameString = userName.getText().toString().trim();
        final String emailString = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String confirmPassString = confirmPassword.getText().toString().trim();


        if (!emailString.equals("") && !pass.equals("") && !nameString.equals("") && !userNameString.equals("") && !confirmPassString.equals(""))
        {
            if (pass.equals(confirmPassString))
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
                            firstLightDB.child("Color").setValue("#FFFFFF");
                            firstLightDB.child("State").setValue("On");


                            DatabaseReference secondLightDB = lightDB.child("Light 2");
                            secondLightDB.child("Color").setValue("#123456");
                            secondLightDB.child("State").setValue("On");


                            DatabaseReference thirdLightDB = lightDB.child("Light 3");
                            thirdLightDB.child("Color").setValue("#FF34EE");
                            thirdLightDB.child("State").setValue("On");



                            Log.d(TAG, "Create user successful");
                            FirebaseUser user = mAuth.getCurrentUser();

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
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }


        }

        else
        {
            Toast.makeText(CreateAccount.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        }
    }


}
