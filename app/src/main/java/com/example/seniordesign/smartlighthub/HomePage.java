package com.example.seniordesign.smartlighthub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {


    Button lightControls;

    Button musicControls;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        lightControls = (Button) findViewById(R.id.lightControls);
        lightControls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePage.this, LightControls.class);
                startActivity(intent);
            }
        });


        musicControls = (Button) findViewById(R.id.musicControls);
        musicControls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePage.this, MusicControls.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.settingsButton)
        {
            startActivity(new Intent(HomePage.this, SettingsPage.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
