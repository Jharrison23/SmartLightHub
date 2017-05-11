package com.example.seniordesign.smartlighthub;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {

    private static final String TAG = "CreateAccount";

    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private Button createAccount;
    private EditText fullName, userName, email, password, confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        init();
    }

    public void init()
    {

        mAuth = FirebaseAuth.getInstance();

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

        createAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String emailAddress = email.getText().toString();

                String pass = password.getText().toString();

                if (!emailAddress.equals("") && !pass.equals(""))
                {
                    mAuth.createUserWithEmailAndPassword(emailAddress, pass);
                    Toast.makeText(CreateAccount.this, "user Signed in", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Toast.makeText(CreateAccount.this, "Fill out email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
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



}
