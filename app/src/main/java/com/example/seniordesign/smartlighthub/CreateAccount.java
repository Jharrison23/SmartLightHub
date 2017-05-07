package com.example.seniordesign.smartlighthub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {

    private DatabaseReference mDatabase;

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
        // Get a reference to the root directory of the application
        mDatabase = FirebaseDatabase.getInstance().getReference();

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
                mDatabase.child("User").setValue(fullName.getText().toString(), userName.getText().toString());
            }
        });
    }
}
