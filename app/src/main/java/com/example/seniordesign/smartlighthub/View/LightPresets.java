package com.example.seniordesign.smartlighthub.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.example.seniordesign.smartlighthub.Controller.PresetsPageAdapter;
import com.example.seniordesign.smartlighthub.Model.Light;
import com.example.seniordesign.smartlighthub.Model.Preset;
import com.example.seniordesign.smartlighthub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class LightPresets extends Fragment {


    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Presets");

    private PresetsPageAdapter presetsPageAdapter;

    FloatingActionButton addPresets;

    RecyclerView presetsRecyclerView;

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

        presetsRecyclerView = (RecyclerView) view.findViewById(R.id.presetsRecyclerView);
        presetsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        presetsPageAdapter = new PresetsPageAdapter(createPresetList(), getContext());

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };



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

    @Override
    public void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    public List<Preset> createPresetList() {

        final List<Preset> presetList = new ArrayList<>();


        ValueEventListener presetEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child: children) {

                    List<Light> lightlist = new ArrayList<>();

                    String name = (String) child.child("Name").getValue();

                    Iterable<DataSnapshot> lightslist = child.child("Lights").getChildren();

                    for (DataSnapshot lil: lightslist)
                    {

                        Light light = lil.getValue(Light.class);

                        Log.d("INHERE ", "jfkdsjklf");
                        Log.d("Lil VALUE ", lil.getValue() + "");
                        lightlist.add(light);

                    }

                    Preset newPreset = new Preset(name, lightlist);

                    Log.d("PRESET VALUE ", "i = " + child + " " + newPreset);

                    presetList.add(newPreset);

                }


                presetsRecyclerView.setHasFixedSize(true);
                presetsRecyclerView.setAdapter(presetsPageAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        userRef.addValueEventListener(presetEventListener);
        return presetList;
    }


}
