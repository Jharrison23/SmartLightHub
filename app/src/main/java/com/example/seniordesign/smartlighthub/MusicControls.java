package com.example.seniordesign.smartlighthub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MusicControls extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_controls);


        Button MusicSpotify = (Button) findViewById(R.id.Spotify_button);
        MusicSpotify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(MusicControls.this, MusicSpotify.class);
                startActivity(intent);
            }
        });
    }




}
