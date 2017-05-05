package com.example.seniordesign.smartlighthub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

       init();
    }

    public void init()
    {
        final Button saveButton = (Button) findViewById(R.id.saveButton);

        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        final Button editButton = (Button) findViewById(R.id.editButton);


        saveButton.setVisibility(View.INVISIBLE);

        cancelButton.setVisibility(View.INVISIBLE);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editButton.setVisibility(View.INVISIBLE);

                saveButton.setVisibility(View.VISIBLE);

                cancelButton.setVisibility(View.VISIBLE);

            }
        });

    }
}
