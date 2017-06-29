package com.example.seniordesign.smartlighthub.View;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.seniordesign.smartlighthub.R;

import org.w3c.dom.Text;

public class MoodCreation extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout firstLightLayout;
    private ConstraintLayout secondLightLayout;
    private ConstraintLayout thirdLightLayout;

    private ImageView imageForMood;

    private EditText firstLightName;
    private EditText secondLightName;
    private EditText thirdLightName;

    private Button firstNextButton;
    private Button secondNextButton;
    private Button doneButton;

    private TextView lightNumberField;




    private Bitmap bitmap;

    private int pageNumber = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_creation);


        firstLightLayout = (ConstraintLayout) findViewById(R.id.firstLightLayout);
        secondLightLayout = (ConstraintLayout) findViewById(R.id.secondLightLayout);
        thirdLightLayout = (ConstraintLayout) findViewById(R.id.thirdLightLayout);

        imageForMood = (ImageView) findViewById(R.id.importedImage);

        firstNextButton = (Button) findViewById(R.id.firstMoodNext);
        firstNextButton.setOnClickListener(this);

        secondNextButton = (Button) findViewById(R.id.secondMoodNext);
        secondNextButton.setOnClickListener(this);

        doneButton = (Button) findViewById(R.id.moodDone);
        doneButton.setOnClickListener(this);

        firstLightName = (EditText) firstLightLayout.findViewById(R.id.firstLayoutName);
        secondLightName = (EditText) secondLightLayout.findViewById(R.id.secondLayoutName);
        thirdLightName = (EditText) thirdLightLayout.findViewById(R.id.thirdLayoutName);

        lightNumberField = (TextView) findViewById(R.id.moodLightNumber);
        lightNumberField.setText("Light 1");


        // Prepare the image for snapshots
        imageForMood.setDrawingCacheEnabled(true);
        imageForMood.buildDrawingCache(true);

        imageForMood.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // If we click the image or drag on the image
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {

                    // Assign a snapshot from the imageview to the bitmap
                    bitmap = imageForMood.getDrawingCache();

                    // Get the pixel which has been touched
                    int pixel = bitmap.getPixel((int) event.getX(),(int) event.getY());


                    // Get the rgb color
                    int red = Color.red(pixel);
                    int green = Color.green(pixel);
                    int blue = Color.blue(pixel);

                    String RGBcolor = red + ", " + green + ", " + blue;

                    if (pageNumber == 1) {

                        firstLightLayout.setBackgroundColor(pixel);
                    }

                    if (pageNumber == 2) {
                        secondLightLayout.setBackgroundColor(pixel);
                    }

                    if (pageNumber == 3) {
                        thirdLightLayout.setBackgroundColor(pixel);
                    }


                }



                return true;
            }
        });




    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.firstMoodNext:

                lightNumberField.setText("Light 2");
                firstLightLayout.setVisibility(View.INVISIBLE);
                secondLightLayout.setVisibility(View.VISIBLE);
                thirdLightLayout.setVisibility(View.INVISIBLE);

                firstNextButton.setVisibility(View.INVISIBLE);
                secondNextButton.setVisibility(View.VISIBLE);
                doneButton.setVisibility(View.INVISIBLE);
                pageNumber = 2;

                break;

            case R.id.secondMoodNext:

                lightNumberField.setText("Light 3");


                firstLightLayout.setVisibility(View.INVISIBLE);
                secondLightLayout.setVisibility(View.INVISIBLE);
                thirdLightLayout.setVisibility(View.VISIBLE);

                firstNextButton.setVisibility(View.INVISIBLE);
                secondNextButton.setVisibility(View.INVISIBLE);
                doneButton.setVisibility(View.VISIBLE);
                pageNumber = 3;
                break;

            case R.id.moodDone:

                break;
        }
    }
}
