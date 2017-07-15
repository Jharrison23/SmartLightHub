package com.example.seniordesign.smartlighthub.View;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.seniordesign.smartlighthub.R;

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


            }
        });
    }




}
