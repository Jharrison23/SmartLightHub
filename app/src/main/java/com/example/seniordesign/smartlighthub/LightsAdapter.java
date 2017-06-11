package com.example.seniordesign.smartlighthub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seniordesign.smartlighthub.models.Light;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jamesharrison on 5/26/17.
 */

public class LightsAdapter extends RecyclerView.Adapter<LightsAdapter.LightsHolder>{


    private List<Light> lightsList;

    private LayoutInflater inflater;


    public LightsAdapter(List<Light> lightsList, Context c)
    {
        this.inflater = LayoutInflater.from(c);

        this.lightsList = lightsList;
    }


    @Override
    public LightsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.light_row, parent, false);




        return new LightsHolder(view);
    }

    @Override
    public void onBindViewHolder(LightsHolder holder, int position) {

        Light light = lightsList.get(position);

        holder.lightName.setText(light.getName());

        holder.lightColor.setBackgroundColor(light.getConvertedColor());

//        String regex = "(\\d+),\\s(\\d+),\\s(\\d+)";
//
//        Pattern pattern = Pattern.compile(regex);
//
//        Matcher matcher = pattern.matcher(light.getColor());
//
//        if (matcher.find())
//        {
//            int newColor = Color.rgb(Integer.valueOf(matcher.group(1)),
//                    Integer.valueOf(matcher.group(2)), Integer.valueOf(matcher.group(3)));
//
//            holder.lightColor.setBackgroundColor(light.getConvertedColor());
//
//        }
//
//        else
//        {
//            Log.d("LightAdapter", "No Match");
//
//            holder.lightColor.setBackgroundColor(Color.rgb(0,0,0));
//
//        }

        holder.lightState.setChecked(light.isState());


    }

    @Override
    public int getItemCount() {


        return lightsList.size();
    }

    class LightsHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {


        private View container;
        private TextView lightName;
        private ImageView lightColor;
        private Switch lightState;




        public LightsHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            lightName = (TextView) itemView.findViewById(R.id.lightName);

            lightColor = (ImageView) itemView.findViewById(R.id.lightColor);

            lightState = (Switch) itemView.findViewById(R.id.lightState);

            container = itemView.findViewById(R.id.lightsContainer);


        }


        @Override
        public void onClick(View v) {


            final List<Light> lightList = new ArrayList<>();

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Lights");

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

            Toast.makeText(v.getContext(),lightsList.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();

            Intent lightInfoIntent = new Intent(v.getContext(), LightInfo.class);

            lightInfoIntent.putExtra("pos", getAdapterPosition());

            v.getContext().startActivity(lightInfoIntent);

            Log.d("LightsAdapter", "Clicked " + getAdapterPosition());
        }


    }







}
