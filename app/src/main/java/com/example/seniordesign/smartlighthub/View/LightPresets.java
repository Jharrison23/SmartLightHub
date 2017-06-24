package com.example.seniordesign.smartlighthub.View;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.seniordesign.smartlighthub.R;

public class LightPresets extends Fragment {

    FloatingActionButton addPresets;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_light_presets, container, false);


        init(view);

        return view;

    }

    public void init(View view)
    {
        addPresets = (FloatingActionButton) view.findViewById(R.id.addPresetsButton);

        addPresets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddPresetsActivity.class));
            }
        });

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
