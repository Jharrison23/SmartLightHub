package com.example.seniordesign.smartlighthub.View;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.seniordesign.smartlighthub.Controller.RoutinePageAdapter;
import com.example.seniordesign.smartlighthub.Model.Light;
import com.example.seniordesign.smartlighthub.Model.Routine;
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

public class Routines extends android.support.v4.app.Fragment {


    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Routines");


    private FloatingActionButton addRoutineButton;
    private RecyclerView routineRecyclerView;
    private RoutinePageAdapter routinePageAdapter;


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

        routineRecyclerView = (RecyclerView) view.findViewById(R.id.routineRecyclerView);
        routineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        routinePageAdapter = new RoutinePageAdapter(createRoutineList(), getContext());



        return view;
    }

    public List<Routine> createRoutineList() {

        final List<Routine> routineList = new ArrayList<>();

        ValueEventListener routineEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child: children) {

                    List<Light> lightList = new ArrayList<>();

                    List<Boolean> listOfDays = new ArrayList<>();

                    String name = (String) child.child("Name").getValue();

                    String time = (String) child.child("Time").getValue();

                    Iterable<DataSnapshot> routineLights = child.child("Lights").getChildren();

                    Iterable<DataSnapshot> routineDays = child.child("Days").getChildren();

                    for (DataSnapshot routineLight: routineLights) {

                        Light light = routineLight.getValue(Light.class);

                        lightList.add(light);
                    }

                    for (DataSnapshot day: routineDays) {

                        if (listOfDays.size() <= 7) {
                            listOfDays.add((Boolean) day.getValue());
                        }
                    }

                    Routine routine = new Routine(name, time, listOfDays, lightList);

                    routineList.add(routine);

                }

                routineRecyclerView.setHasFixedSize(true);
                routineRecyclerView.setAdapter(routinePageAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        userRef.addValueEventListener(routineEventListener);

        return routineList;
    }

}
