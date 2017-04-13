package com.example.jamesharrison.smartlighthub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Set;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, SettingsPage.class);
                startActivity(intent);
            }
        });

        Button lightControls = (Button) findViewById(R.id.lightControls);
        lightControls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePage.this, LightControls.class);
                startActivity(intent);
            }
        });


        Button musicControls = (Button) findViewById(R.id.musicControls);
        musicControls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePage.this, MusicControls.class);
                startActivity(intent);
            }
        });

    }
}
