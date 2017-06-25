package com.example.seniordesign.smartlighthub.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seniordesign.smartlighthub.R;
import com.example.seniordesign.smartlighthub.View.LightInfo;
import com.example.seniordesign.smartlighthub.Model.Light;
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

        holder.lightState.setChecked(light.isState());

        holder.lightContainer.setBackgroundColor(light.getConvertedColor());
    }

    @Override
    public int getItemCount() {


        return lightsList.size();
    }

    class LightsHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private TextView lightName;
        private Switch lightState;
        private LinearLayout lightContainer;




        public LightsHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            lightContainer = (LinearLayout) itemView.findViewById(R.id.lightsContainer);

            lightName = (TextView) itemView.findViewById(R.id.firstLightName);

            lightState = (Switch) itemView.findViewById(R.id.firstLightState);


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
