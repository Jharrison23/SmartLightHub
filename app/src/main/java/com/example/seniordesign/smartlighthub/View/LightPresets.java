package com.example.seniordesign.smartlighthub.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

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


                final EditText input = new EditText(getActivity());

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                input.setLayoutParams(lp);
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("New Preset");
                alertDialog.setView(input);
                alertDialog.setMessage("Please Enter Preset Name");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                Intent AddPresetIntent = new Intent(getActivity(), AddPresetsActivity.class);

                                AddPresetIntent.putExtra("Preset Name", input.getText().toString());

                                startActivity(AddPresetIntent);

                            }
                        });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();

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
