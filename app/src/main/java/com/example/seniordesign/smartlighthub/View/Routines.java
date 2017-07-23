package com.example.seniordesign.smartlighthub.View;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.BoolRes;
import android.support.annotation.IntegerRes;
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
import android.widget.Toast;

import com.example.seniordesign.smartlighthub.Controller.AlarmReciever;
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

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Routines extends android.support.v4.app.Fragment {


    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Routines");


    private FloatingActionButton addRoutineButton;
    private RecyclerView routineRecyclerView;
    private RoutinePageAdapter routinePageAdapter;

    private boolean alarmsCreated;


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

    @Override
    public void onStart() {
        super.onStart();
        onStartAlarmManager();
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

                    String routineHour = (String) child.child("Hour").getValue();

                    String routineMinute = (String) child.child("Minute").getValue();

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

                    Routine routine = new Routine(name, routineHour, routineMinute, listOfDays, lightList);

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

    public void onStartAlarmManager() {

        alarmsCreated = false;

        ValueEventListener routineEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int requestCodeIndex = 0;

                ArrayList<String> alarmDay = new ArrayList<>();

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child: children) {

                    int currentDay = 0;

                    List<Light> lightList = new ArrayList<>();

                    List<Boolean> listOfDays = new ArrayList<>();

                    String name = (String) child.child("Name").getValue();

                    String routineHour = (String) child.child("Hour").getValue();

                    String routineMinute = (String) child.child("Minute").getValue();

                    Iterable<DataSnapshot> routineLights = child.child("Lights").getChildren();

                    Iterable<DataSnapshot> routineDays = child.child("Days").getChildren();

                    for (DataSnapshot routineLight: routineLights) {

                        Light light = routineLight.getValue(Light.class);

                        lightList.add(light);
                    }

                    for (DataSnapshot day: routineDays) {

                        if (listOfDays.size() <= 7) {

                            Boolean isAlarmDay = (Boolean) day.getValue();

                            listOfDays.add(isAlarmDay);

                            switch (currentDay){

                                case 0:

                                    if (isAlarmDay) {
                                        alarmDay.add("Sunday");
                                    }

                                    break;

                                case 1:

                                    if (isAlarmDay) {
                                        alarmDay.add("Monday");
                                    }

                                    break;

                                case 2:

                                    if (isAlarmDay) {
                                        alarmDay.add("Tuesday");
                                    }

                                    break;

                                case 3:

                                    if (isAlarmDay) {
                                        alarmDay.add("Wednesday");
                                    }

                                    break;

                                case 4:

                                    if (isAlarmDay) {
                                        alarmDay.add("Thursday");
                                    }

                                    break;

                                case 5:

                                    if (isAlarmDay) {
                                        alarmDay.add("Friday");
                                    }

                                    break;

                                case 6:

                                    if (isAlarmDay) {
                                        alarmDay.add("Saturday");
                                    }

                                    break;

                            }

                            currentDay++;
                        }
                    }


                    if (routineHour != null && routineMinute != null && !alarmsCreated) {


                        Calendar calendar = Calendar.getInstance();

                        Date date = calendar.getTime();

                        String format = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());

                        int intHour = Integer.parseInt(routineHour);

                        int intMinute = Integer.parseInt(routineMinute);

                        Log.d("Alarm Day of the week", format + " " + alarmDay.contains(format) + " Hour " + calendar.get(Calendar.HOUR_OF_DAY) + " Minute " + calendar.get(Calendar.MINUTE));

                        if (alarmDay.contains(format) && intHour >= calendar.get(Calendar.HOUR_OF_DAY)) {

                            if (intHour > calendar.get(Calendar.HOUR_OF_DAY)) {

                                // set the calendar with the hour and minute
                                calendar.set(Calendar.HOUR_OF_DAY, intHour);

                                calendar.set(Calendar.MINUTE, intMinute);

                                Intent alarmIntent = new Intent(getActivity(), AlarmReciever.class);

                                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), requestCodeIndex, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

                                alarmManager.setExact(alarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                                Log.d("Alarm Added", routineHour + " : " + routineMinute);

                                requestCodeIndex++;
                            }

                            else if (intMinute > calendar.get(Calendar.MINUTE)) {

                                // set the calendar with the hour and minute
                                calendar.set(Calendar.HOUR_OF_DAY, intHour);

                                calendar.set(Calendar.MINUTE, intMinute);

                                Intent alarmIntent = new Intent(getActivity(), AlarmReciever.class);

                                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), requestCodeIndex, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

                                alarmManager.setExact(alarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                                Log.d("Alarm Added", routineHour + " : " + routineMinute);

                                requestCodeIndex++;
                            }

                        }
                    }
                }
                alarmsCreated = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        userRef.addValueEventListener(routineEventListener);

    }



}
