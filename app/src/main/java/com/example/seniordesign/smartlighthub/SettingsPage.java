package com.example.seniordesign.smartlighthub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsPage extends AppCompatActivity implements View.OnClickListener {


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
}
