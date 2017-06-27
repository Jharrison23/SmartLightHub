package com.example.seniordesign.smartlighthub.Controller;

import android.content.Context;
import android.content.Intent;
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
import com.example.seniordesign.smartlighthub.View.LightInfo;
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

    class PresetsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView firstLightColor;
        private ImageView secondLightColor;
        private ImageView thirdLightColor;

        private TextView presetName;

        private CardView presetPageCardView;



        public PresetsHolder(View itemView) {
            super(itemView);


            //itemView.setOnClickListener(this);

            presetPageCardView = (CardView) itemView.findViewById(R.id.presetCardView);

            firstLightColor = (ImageView) itemView.findViewById(R.id.firstLightColor_preset);
            firstLightColor.setOnClickListener(this);

            secondLightColor = (ImageView) itemView.findViewById(R.id.secondLightColor_preset);
            secondLightColor.setOnClickListener(this);

            thirdLightColor = (ImageView) itemView.findViewById(R.id.thirdLightColor_preset);
            thirdLightColor.setOnClickListener(this);

            presetName = (TextView) itemView.findViewById(R.id.presetName_preset);
            presetName.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            final List<Light> lightList = new ArrayList<>();

            String currentPreset = presetList.get(getAdapterPosition()).getName();

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Presets").child(currentPreset).child("Lights");

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                    for (DataSnapshot child : children) {

                        Light newLight = child.getValue(Light.class);

                        lightList.add(newLight);

                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            switch (v.getId())
            {
                case R.id.firstLightColor_preset:

                    Toast.makeText(v.getContext(),presetList.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();

                    Intent firstLightInfoIntent = new Intent(v.getContext(), PresetLightInfo.class);

                    firstLightInfoIntent.putExtra("pos", 0);
                    firstLightInfoIntent.putExtra("preset", currentPreset);

                    v.getContext().startActivity(firstLightInfoIntent);

                    Log.d("PresetPageAdapter", "Clicked " + getAdapterPosition());

                    break;

                case R.id.secondLightColor_preset:

                    Toast.makeText(v.getContext(),presetList.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();

                    Intent secondLightInfoIntent = new Intent(v.getContext(), PresetLightInfo.class);

                    secondLightInfoIntent.putExtra("pos", 1);
                    secondLightInfoIntent.putExtra("preset", currentPreset);

                    v.getContext().startActivity(secondLightInfoIntent);

                    Log.d("PresetPageAdapter", "Clicked " + getAdapterPosition());

                    break;

                case R.id.thirdLightColor_preset:

                    Toast.makeText(v.getContext(),presetList.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();

                    Intent thirdLightInfoIntent = new Intent(v.getContext(), PresetLightInfo.class);

                    thirdLightInfoIntent.putExtra("pos", 2);
                    thirdLightInfoIntent.putExtra("preset", currentPreset);

                    v.getContext().startActivity(thirdLightInfoIntent);

                    Log.d("PresetPageAdapter", "Clicked " + getAdapterPosition());

                    break;



            }

//
//            Toast.makeText(v.getContext(),presetList.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
//
//            Intent lightInfoIntent = new Intent(v.getContext(), LightInfo.class);
//
//            lightInfoIntent.putExtra("pos", getAdapterPosition());
//
//            v.getContext().startActivity(lightInfoIntent);
//
//            Log.d("PresetPageAdapter", "Clicked " + getAdapterPosition());
        }

    }
}
