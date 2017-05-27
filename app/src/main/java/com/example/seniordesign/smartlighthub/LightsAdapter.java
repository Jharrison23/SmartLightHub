package com.example.seniordesign.smartlighthub;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.seniordesign.smartlighthub.models.Light;

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

        holder.lightColor.setBackgroundColor(Color.parseColor(light.getColor()));

        holder.lightState.setChecked(light.isState());


    }

    @Override
    public int getItemCount() {


        return lightsList.size();
    }

    class LightsHolder extends RecyclerView.ViewHolder
    {


        private View container;
        private TextView lightName;
        private ImageView lightColor;
        private Switch lightState;




        public LightsHolder(View itemView) {
            super(itemView);


            lightName = (TextView) itemView.findViewById(R.id.lightName);

            lightColor = (ImageView) itemView.findViewById(R.id.lightColor);

            lightState = (Switch) itemView.findViewById(R.id.lightState);

            container = itemView.findViewById(R.id.lightsContainer);






        }






    }







}
