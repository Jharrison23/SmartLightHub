package com.example.seniordesign.smartlighthub.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.seniordesign.smartlighthub.Controller.BottomNavigation;
import com.example.seniordesign.smartlighthub.R;

import yuku.ambilwarna.AmbilWarnaDialog;


public class AddPresetsActivity extends AppCompatActivity implements View.OnClickListener{
    
    private EditText firstLightName;
    private ImageView firstLightColor;
    private Switch firstLightState;
    private Button firstPageNext;

    private EditText secondLightName;
    private ImageView secondLightColor;
    private Switch secondLightState;
    private Button secondPageNext;

    private EditText thirdLightName;
    private ImageView thirdLightColor;
    private Switch thirdLightState;
    private Button thirdPageNext;

    private int pageNumber;

    private int defaultColor;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_presets);

        init();
    }

    @Override
    public void onBackPressed() {

        if (pageNumber == 1) {
            super.onBackPressed();
        }

        else if (pageNumber == 2) {

            firstLightName.setVisibility(View.VISIBLE);
            firstLightColor.setVisibility(View.VISIBLE);
            firstLightState.setVisibility(View.VISIBLE);
            firstPageNext.setVisibility(View.VISIBLE);

            secondLightName.setVisibility(View.INVISIBLE);
            secondLightColor.setVisibility(View.INVISIBLE);
            secondLightState.setVisibility(View.INVISIBLE);
            secondPageNext.setVisibility(View.INVISIBLE);

            thirdLightName.setVisibility(View.INVISIBLE);
            thirdLightColor.setVisibility(View.INVISIBLE);
            thirdLightState.setVisibility(View.INVISIBLE);
            thirdPageNext.setVisibility(View.INVISIBLE);

            pageNumber = 1;
        }

        else if (pageNumber == 3) {


            firstLightName.setVisibility(View.INVISIBLE);
            firstLightColor.setVisibility(View.INVISIBLE);
            firstLightState.setVisibility(View.INVISIBLE);
            firstPageNext.setVisibility(View.INVISIBLE);

            secondLightName.setVisibility(View.VISIBLE);
            secondLightColor.setVisibility(View.VISIBLE);
            secondLightState.setVisibility(View.VISIBLE);
            secondPageNext.setVisibility(View.VISIBLE);

            thirdLightName.setVisibility(View.INVISIBLE);
            thirdLightColor.setVisibility(View.INVISIBLE);
            thirdLightState.setVisibility(View.INVISIBLE);
            thirdPageNext.setVisibility(View.INVISIBLE);

            pageNumber = 2;


        }

    }

    public void init() {
        
        firstLightName  = (EditText) findViewById(R.id.firstLightName);
        firstLightColor = (ImageView) findViewById(R.id.firstLightColor);
        firstLightColor.setOnClickListener(this);
        firstLightState = (Switch) findViewById(R.id.firstLightState);
        firstPageNext = (Button) findViewById(R.id.firstPageNext);
        firstPageNext.setOnClickListener(this);



        secondLightName  = (EditText) findViewById(R.id.secondLightName);
        secondLightColor = (ImageView) findViewById(R.id.secondLightColor);
        secondLightColor.setOnClickListener(this);
        secondLightState = (Switch) findViewById(R.id.secondLightState);
        secondPageNext = (Button) findViewById(R.id.secondPageNext);
        secondPageNext.setOnClickListener(this);

        thirdLightName  = (EditText) findViewById(R.id.thirdLightName);
        thirdLightColor = (ImageView) findViewById(R.id.thirdLightColor);
        thirdLightColor.setOnClickListener(this);
        thirdLightState = (Switch) findViewById(R.id.thirdLightState);
        thirdPageNext = (Button) findViewById(R.id.thirdPageNext);
        thirdPageNext.setOnClickListener(this);

        pageNumber = 1;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            
            case R.id.firstPageNext:
                
                firstLightName.setVisibility(View.INVISIBLE);
                firstLightColor.setVisibility(View.INVISIBLE);
                firstLightState.setVisibility(View.INVISIBLE);
                firstPageNext.setVisibility(View.INVISIBLE);

                secondLightName.setVisibility(View.VISIBLE);
                secondLightColor.setVisibility(View.VISIBLE);
                secondLightState.setVisibility(View.VISIBLE);
                secondPageNext.setVisibility(View.VISIBLE);

                thirdLightName.setVisibility(View.INVISIBLE);
                thirdLightColor.setVisibility(View.INVISIBLE);
                thirdLightState.setVisibility(View.INVISIBLE);
                thirdPageNext.setVisibility(View.INVISIBLE);
                pageNumber = 2;


                break;

            case R.id.secondPageNext:

                firstLightName.setVisibility(View.INVISIBLE);
                firstLightColor.setVisibility(View.INVISIBLE);
                firstLightState.setVisibility(View.INVISIBLE);
                firstPageNext.setVisibility(View.INVISIBLE);

                secondLightName.setVisibility(View.INVISIBLE);
                secondLightColor.setVisibility(View.INVISIBLE);
                secondLightState.setVisibility(View.INVISIBLE);
                secondPageNext.setVisibility(View.INVISIBLE);

                thirdLightName.setVisibility(View.VISIBLE);
                thirdLightColor.setVisibility(View.VISIBLE);
                thirdLightState.setVisibility(View.VISIBLE);
                thirdPageNext.setVisibility(View.VISIBLE);
                pageNumber = 3;



                break;

            case R.id.thirdPageNext:

                startActivity(new Intent(AddPresetsActivity.this, BottomNavigation.class));

                break;

            case R.id.firstLightColor:

                openColorPickerDialog(false, firstLightColor);
                break;


            case R.id.secondLightColor:

                openColorPickerDialog(false, secondLightColor);
                break;

            case R.id.thirdLightColor:

                openColorPickerDialog(false, thirdLightColor);
                break;
        }
        
    }


    // Color picker 2
    private void openColorPickerDialog(boolean AlphaSupport, final ImageView lightColor) {

        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(AddPresetsActivity.this, defaultColor, AlphaSupport, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int color) {

                defaultColor = color;

                lightColor.setBackgroundColor(color);


            }

            @Override
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {

                Toast.makeText(AddPresetsActivity.this, "Color Picker Closed", Toast.LENGTH_SHORT).show();
            }
        });

        ambilWarnaDialog.show();

    }



}
