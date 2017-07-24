package com.example.seniordesign.smartlighthub.Controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seniordesign.smartlighthub.Model.Light;
import com.example.seniordesign.smartlighthub.Model.Preset;
import com.example.seniordesign.smartlighthub.R;
import com.example.seniordesign.smartlighthub.View.HomePage;
import com.example.seniordesign.smartlighthub.View.PresetLightInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * Created by jamesharrison on 6/25/17.
 */

public class PresetsPageAdapter extends RecyclerView.Adapter<PresetsPageAdapter.PresetsHolder>{

    private List<Preset> presetList;

    private LayoutInflater inflater;

    public PresetsPageAdapter(List<Preset> presetList, Context c) {
        this.inflater = LayoutInflater.from(c);

        this.presetList = presetList;
    }


    @Override
    public PresetsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.preset_row_element, parent, false);

        return new PresetsHolder(view);
    }

    @Override
    public void onBindViewHolder(PresetsPageAdapter.PresetsHolder holder, int position) {

        Preset preset = presetList.get(position);

        List<Light> presetLightList = preset.getLightList();

        Log.d("PRSET NAME ", preset.getName() + "");
        holder.presetName.setText(preset.getName());

        for (int i = 0; i < presetLightList.size(); i++)
        {
            if (i == 0) {

                holder.firstLightColor.setBackgroundColor(presetLightList.get(0).getConvertedColor());
                Log.d("Light 1 color ", presetLightList.get(0).getColor() + "");
            }

            else if (i == 1) {

                holder.secondLightColor.setBackgroundColor(presetLightList.get(1).getConvertedColor());
                Log.d("Light 2 color ", presetLightList.get(1).getColor() + "");

            }

            else if (i == 2) {
                holder.thirdLightColor.setBackgroundColor(presetLightList.get(2).getConvertedColor());
                Log.d("Light 3 color ", presetLightList.get(2).getColor() + "");

            }

        }

    }

    @Override
    public int getItemCount() {
        return presetList.size();
    }

    class PresetsHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private ImageView firstLightColor;
        private ImageView secondLightColor;
        private ImageView thirdLightColor;


        private TextView presetName;

        private ConstraintLayout presetConstraintsLayout;
        private CardView presetPageCardView;


        private int defautColor;


        public PresetsHolder(View itemView) {
            super(itemView);

            presetConstraintsLayout = (ConstraintLayout) itemView.findViewById(R.id.presetConstraints);
            presetPageCardView = (CardView) itemView.findViewById(R.id.presetCardView);

            firstLightColor = (ImageView) itemView.findViewById(R.id.firstLightColor_preset);
            firstLightColor.setOnClickListener(this);
            firstLightColor.setOnLongClickListener(this);

            secondLightColor = (ImageView) itemView.findViewById(R.id.secondLightColor_preset);
            secondLightColor.setOnClickListener(this);
            secondLightColor.setOnLongClickListener(this);

            thirdLightColor = (ImageView) itemView.findViewById(R.id.thirdLightColor_preset);
            thirdLightColor.setOnClickListener(this);
            thirdLightColor.setOnLongClickListener(this);

            presetName = (TextView) itemView.findViewById(R.id.presetName_preset);
            presetName.setClickable(true);
            presetName.setOnClickListener(this);
            presetName.setOnLongClickListener(this);
        }

        @Override
        public void onClick(final View v) {

            final View view = v;

            final List<Light> lightList = new ArrayList<>();

            final String currentPreset = presetList.get(getAdapterPosition()).getName();

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

            final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Presets").child(currentPreset).child("Lights");

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                    for (DataSnapshot child : children) {

                        Light newLight = child.getValue(Light.class);

                        lightList.add(newLight);

                    }


                    switch (view.getId()) {

                        case R.id.presetName_preset:


                            Bundle homepageIntent = new Bundle();

                            homepageIntent.putString("light1Name", lightList.get(0).getName());
                            homepageIntent.putBoolean("light1State", lightList.get(0).isState());
                            homepageIntent.putString("light1Color", lightList.get(0).getColor());

                            homepageIntent.putString("light2Name", lightList.get(1).getName());
                            homepageIntent.putBoolean("light2State", lightList.get(1).isState());
                            homepageIntent.putString("light2Color", lightList.get(1).getColor());

                            homepageIntent.putString("light3Name", lightList.get(2).getName());
                            homepageIntent.putBoolean("light3State", lightList.get(2).isState());
                            homepageIntent.putString("light3Color", lightList.get(2).getColor());


                            BottomNavigation bottomNavigation = (BottomNavigation)v.getContext();

                            Fragment homeFragment = new HomePage();

                            homeFragment.setArguments(homepageIntent);

                            FragmentManager fragmentManager = bottomNavigation.getSupportFragmentManager();

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            bottomNavigation.setSelected(1);

                            fragmentTransaction.replace(R.id.container, homeFragment,"PresetsAdapter").commit();

                            break;


                    }


                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            switch (view.getId())
            {

                case R.id.firstLightColor_preset:

                    final int firstInitialColor = ((ColorDrawable) firstLightColor.getBackground()).getColor();
                    openColorPickerDialog(false, firstInitialColor, firstLightColor, 1);
                    break;

                case R.id.secondLightColor_preset:

                    final int secondInitialColor = ((ColorDrawable) secondLightColor.getBackground()).getColor();
                    openColorPickerDialog(false, secondInitialColor, secondLightColor, 2);

                    break;

                case R.id.thirdLightColor_preset:
                    final int thirdInitialColor = ((ColorDrawable) thirdLightColor.getBackground()).getColor();
                    openColorPickerDialog(false, thirdInitialColor, thirdLightColor, 3);

                    break;

            }


        }


        // Color picker 2
        private boolean openColorPickerDialog(boolean AlphaSupport, int defaultColor, final ImageView currentLightColor, final int lightPosition) {

            AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(itemView.getContext(), defaultColor, AlphaSupport, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onOk(AmbilWarnaDialog ambilWarnaDialog, int color) {

                    currentLightColor.setBackgroundColor(color);

                    updateDatabaseLightColor(currentLightColor, lightPosition, getAdapterPosition());
                    //Toast.makeText(itemView.getContext(), "Light " + lightPosition + " updated", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {

                    //Toast.makeText(itemView.getContext(), "Color Picker Closed", Toast.LENGTH_SHORT).show();
                }
            });
            ambilWarnaDialog.show();

            return true;

        }

        public boolean updateDatabaseLightColor(ImageView lightColorView, final int lightNumber, int position) {



            final String currentPreset = presetList.get(position).getName();

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

            final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Presets").child(currentPreset).child("Lights");

            DatabaseReference lightRef = userRef.child("Light " + lightNumber);

            Drawable lightDrawableColor = (Drawable) lightColorView.getBackground();

            int colorInt = ((ColorDrawable) lightDrawableColor).getColor();

            int red = Color.red(colorInt);
            int green = Color.green(colorInt);
            int blue = Color.blue(colorInt);

            String RGBcolor = red + ", " + green + ", " + blue;

            lightRef.child("Color").setValue(RGBcolor);

            presetList.clear();

            return true;
        }

        @Override
        public boolean onLongClick(View v) {

            final String currentPreset = presetList.get(getAdapterPosition()).getName();

            switch (v.getId()) {
                case R.id.presetName_preset:

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                    final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                    DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Presets");

                    userRef.child(currentPreset).removeValue();

                    Toast.makeText(v.getContext(), "Deleted " + currentPreset, Toast.LENGTH_SHORT).show();

                    presetList.remove(getAdapterPosition());

                    presetList.clear();

                    notifyItemRemoved(getAdapterPosition());

                    break;

                case R.id.firstLightColor_preset:

                    Intent firstLightInfoIntent = new Intent(v.getContext(), PresetLightInfo.class);

                    firstLightInfoIntent.putExtra("pos", 0);
                    firstLightInfoIntent.putExtra("preset", currentPreset);

                    v.getContext().startActivity(firstLightInfoIntent);

                    Log.d("PresetPageAdapter", "Clicked " + getAdapterPosition());

                    break;

                case R.id.secondLightColor_preset:

                    Intent secondLightInfoIntent = new Intent(v.getContext(), PresetLightInfo.class);

                    secondLightInfoIntent.putExtra("pos", 1);
                    secondLightInfoIntent.putExtra("preset", currentPreset);

                    v.getContext().startActivity(secondLightInfoIntent);

                    Log.d("PresetPageAdapter", "Clicked " + getAdapterPosition());

                    break;

                case R.id.thirdLightColor_preset:

                    Intent thirdLightInfoIntent = new Intent(v.getContext(), PresetLightInfo.class);

                    thirdLightInfoIntent.putExtra("pos", 2);
                    thirdLightInfoIntent.putExtra("preset", currentPreset);

                    v.getContext().startActivity(thirdLightInfoIntent);

                    Log.d("PresetPageAdapter", "Clicked " + getAdapterPosition());

                    break;

            }

            return true;
        }
    }
}
