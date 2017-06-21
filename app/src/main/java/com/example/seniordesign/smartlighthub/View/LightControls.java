package com.example.seniordesign.smartlighthub.View;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.seniordesign.smartlighthub.R;

public class LightControls extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_controls);

        getActionBar().setTitle("Presets");


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.light_preset_menu, menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }
//



}
