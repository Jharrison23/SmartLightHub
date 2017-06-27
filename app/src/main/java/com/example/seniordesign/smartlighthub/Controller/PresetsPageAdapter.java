package com.example.seniordesign.smartlighthub.Controller;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seniordesign.smartlighthub.Model.Light;
import com.example.seniordesign.smartlighthub.Model.Preset;
import com.example.seniordesign.smartlighthub.R;

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


            itemView.setOnClickListener(this);

            presetPageCardView = (CardView) itemView.findViewById(R.id.presetCardView);

            firstLightColor = (ImageView) itemView.findViewById(R.id.firstLightColor_preset);

            secondLightColor = (ImageView) itemView.findViewById(R.id.secondLightColor_preset);

            thirdLightColor = (ImageView) itemView.findViewById(R.id.thirdLightColor_preset);

            presetName = (TextView) itemView.findViewById(R.id.presetName_preset);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
