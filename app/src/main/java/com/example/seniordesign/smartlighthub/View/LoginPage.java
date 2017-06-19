package com.example.seniordesign.smartlighthub.View;

import android.app.Activity;
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

import com.example.seniordesign.smartlighthub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginPage extends Activity {

    private static final String TAG = "LoginPage";

    private FirebaseAuth mAuth;

    private EditText email;

    private EditText password;

    private Button enterApp;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.emailField);

        password = (EditText) findViewById(R.id.passwordField);

        enterApp = (Button) findViewById(R.id.enterApp);

        enterApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();

            }
        });

    }

    public void onStart()
    {
        super.onStart();

    }

    public void login()
    {
        String emailAddress = email.getText().toString().trim();

        String passwordString = password.getText().toString().trim();

        if (!emailAddress.equals("") && !passwordString.equals(""))
        {
            progressDialog.setMessage("Signing in please wait....");
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(emailAddress, passwordString).addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {

                        Log.d(TAG, "Sign in successful");
                        Toast.makeText(LoginPage.this, "Sign in Successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginPage.this, HomePage.class);
                        startActivity(intent);
                        progressDialog.cancel();

                    }

                    else
                    {
                        Log.w(TAG, "Sign in unsuccessful");
                        Toast.makeText(LoginPage.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                }
            });

        }
    }


}
