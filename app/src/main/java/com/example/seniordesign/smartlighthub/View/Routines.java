package com.example.seniordesign.smartlighthub.View;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.seniordesign.smartlighthub.R;

public class Routines extends android.support.v4.app.Fragment {


    private FloatingActionButton addRoutineButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_routines, container, false);

        addRoutineButton = (FloatingActionButton) view.findViewById(R.id.addRoutine);

        addRoutineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addRoutineIntent = new Intent(getActivity(), AddRoutine.class);

                startActivity(addRoutineIntent);
            }
        });

        return view;
    }

}
