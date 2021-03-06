package com.example.seniordesign.smartlighthub.View;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seniordesign.smartlighthub.Controller.BottomNavigation;
import com.example.seniordesign.smartlighthub.Model.Light;
import com.example.seniordesign.smartlighthub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MoodCreation extends AppCompatActivity implements View.OnClickListener{

    private static final int SELECTED_PICTURE = 100;

    private Uri uri;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference presetsRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Presets");

    private DatabaseReference lightsRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Lights");

    private List<Light> presetLightList;


    private ConstraintLayout firstLightLayout;
    private ConstraintLayout secondLightLayout;
    private ConstraintLayout thirdLightLayout;

    private ImageView imageForMood;
    private ImageButton uploadImage;

    private EditText firstLightName;
    private EditText secondLightName;
    private EditText thirdLightName;

    private Button firstNextButton;
    private Button secondNextButton;
    private Button doneButton;

    private Switch firstLightSwitch;
    private Switch secondLightSwitch;
    private Switch thirdLightSwitch;


    private TextView lightNumberField;

    private String presetName;



    private Bitmap bitmap;

    private int pageNumber = 1;


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_creation);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Create Mood");

        }


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };


        Bundle extras = getIntent().getExtras();

        if (extras != null)
        {
            presetName = extras.getString("Preset Name");

            //Toast.makeText(this, presetName, Toast.LENGTH_SHORT).show();
        }


        firstLightLayout = (ConstraintLayout) findViewById(R.id.firstLightLayout);
        secondLightLayout = (ConstraintLayout) findViewById(R.id.secondLightLayout);
        thirdLightLayout = (ConstraintLayout) findViewById(R.id.thirdLightLayout);

        imageForMood = (ImageView) findViewById(R.id.importedImage);

        uploadImage = (ImageButton) findViewById(R.id.uploadImage);
        uploadImage.setOnClickListener(this);

        firstNextButton = (Button) findViewById(R.id.firstMoodNext);
        firstNextButton.setOnClickListener(this);

        secondNextButton = (Button) findViewById(R.id.secondMoodNext);
        secondNextButton.setOnClickListener(this);

        doneButton = (Button) findViewById(R.id.moodDone);
        doneButton.setOnClickListener(this);

        firstLightName = (EditText) firstLightLayout.findViewById(R.id.firstLayoutName);
        firstLightSwitch = (Switch) firstLightLayout.findViewById(R.id.firstLayoutSwitch);

        secondLightName = (EditText) secondLightLayout.findViewById(R.id.secondLayoutName);
        secondLightSwitch = (Switch) secondLightLayout.findViewById(R.id.secondLayoutSwitch);

        thirdLightName = (EditText) thirdLightLayout.findViewById(R.id.thirdLayoutName);
        thirdLightSwitch = (Switch) thirdLightLayout.findViewById(R.id.thirdLayoutSwitch);

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

        presetLightList = new ArrayList<>();

        lightsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child: children) {

                    Light currentLight = child.getValue(Light.class);
                    presetLightList.add(currentLight);

                }

                firstLightName.setText(presetLightList.get(0).getName());
                secondLightName.setText(presetLightList.get(1).getName());
                thirdLightName.setText(presetLightList.get(2).getName());

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

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
                savePreset();
                break;

            // Open user galler
            case R.id.uploadImage:
                Intent uploadIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                startActivityForResult(uploadIntent, SELECTED_PICTURE);

                break;
        }
    }

    // Method for saving the users selected image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         if (requestCode == SELECTED_PICTURE && resultCode == RESULT_OK) {

             uri = data.getData();

             imageForMood.setImageURI(uri);
        }
    }

    public void savePreset()
    {
        DatabaseReference currentPreset = presetsRef.child(presetName);

        currentPreset.child("Name").setValue(presetName);

        DatabaseReference lightRef = currentPreset.child("Lights");

        DatabaseReference firstLightPresetRef = lightRef.child("Light 1");
        firstLightPresetRef.child("Name").setValue(firstLightName.getText().toString());
        Drawable firstLightDrawableColor = (Drawable) firstLightLayout.getBackground();

        int firstColorInt = ((ColorDrawable) firstLightDrawableColor).getColor();

        int firstRed = Color.red(firstColorInt);                                                                                                                                                        startActivity(getIntent());
        int firstGreen = Color.green(firstColorInt);
        int firstBlue = Color.blue(firstColorInt);

        String firstRGBcolor = firstRed + ", " + firstGreen + ", " + firstBlue;

        firstLightPresetRef.child("Color").setValue(firstRGBcolor);
        firstLightPresetRef.child("State").setValue(firstLightSwitch.isChecked());



        DatabaseReference secondLightPresetRef = lightRef.child("Light 2");
        secondLightPresetRef.child("Name").setValue(secondLightName.getText().toString());
        Drawable secondLightDrawableColor = (Drawable) secondLightLayout.getBackground();

        int secondColorInt = ((ColorDrawable) secondLightDrawableColor).getColor();

        int secondRed = Color.red(secondColorInt);                                                                                                                                                        startActivity(getIntent());
        int secondGreen = Color.green(secondColorInt);
        int secondBlue = Color.blue(secondColorInt);

        String secondRGBcolor = secondRed + ", " + secondGreen + ", " + secondBlue;


        secondLightPresetRef.child("Color").setValue(secondRGBcolor);
        secondLightPresetRef.child("State").setValue(secondLightSwitch.isChecked());



        DatabaseReference thirdLightPresetRef = lightRef.child("Light 3");
        thirdLightPresetRef.child("Name").setValue(thirdLightName.getText().toString());
        Drawable thirdLightDrawableColor = (Drawable) thirdLightLayout.getBackground();

        int thirdColorInt = ((ColorDrawable) thirdLightDrawableColor).getColor();

        int thirdRed = Color.red(thirdColorInt);                                                                                                                                                        startActivity(getIntent());
        int thirdGreen = Color.green(thirdColorInt);
        int thirdBlue = Color.blue(thirdColorInt);

        String thirdRGBcolor = thirdRed + ", " + thirdGreen + ", " + thirdBlue;

        thirdLightPresetRef.child("Color").setValue(thirdRGBcolor);
        thirdLightPresetRef.child("State").setValue(thirdLightSwitch.isChecked());


        startActivity(new Intent(MoodCreation.this, BottomNavigation.class));

    }


    @Override
    public void onBackPressed() {

        if (pageNumber == 1) {
            super.onBackPressed();
        }

        else if (pageNumber == 2) {


            lightNumberField.setText("Light 1");

            firstLightLayout.setVisibility(View.VISIBLE);
            secondLightLayout.setVisibility(View.INVISIBLE);
            thirdLightLayout.setVisibility(View.INVISIBLE);

            firstNextButton.setVisibility(View.VISIBLE);
            secondNextButton.setVisibility(View.INVISIBLE);
            doneButton.setVisibility(View.INVISIBLE);
            pageNumber = 1;

        }

        else if (pageNumber == 3) {

            lightNumberField.setText("Light 2");

            firstLightLayout.setVisibility(View.INVISIBLE);
            secondLightLayout.setVisibility(View.VISIBLE);
            thirdLightLayout.setVisibility(View.INVISIBLE);

            firstNextButton.setVisibility(View.INVISIBLE);
            secondNextButton.setVisibility(View.VISIBLE);
            doneButton.setVisibility(View.INVISIBLE);
            pageNumber = 2;
        }
    }
}
