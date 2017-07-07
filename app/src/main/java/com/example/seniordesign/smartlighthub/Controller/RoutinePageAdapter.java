package com.example.seniordesign.smartlighthub.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.seniordesign.smartlighthub.Model.Routine;
import com.example.seniordesign.smartlighthub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by jamesharrison on 7/6/17.
 */

public class RoutinePageAdapter extends RecyclerView.Adapter<RoutinePageAdapter.RoutineHolder>{

    private List<Routine> routineList;

    private LayoutInflater inflater;

    public RoutinePageAdapter(List<Routine> routineList, Context context) {

        this.inflater = LayoutInflater.from(context);

        this.routineList = routineList;
    }

    @Override
    public RoutineHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.routine_row_element, parent, false);

        return new RoutineHolder(view);

    }

    @Override
    public void onBindViewHolder(RoutineHolder holder, int position) {

        Routine routine = routineList.get(position);

        holder.sundayToggle.setChecked(routine.getDays().get(0));
        holder.mondayToggle.setChecked(routine.getDays().get(1));
        holder.tuesdayToggle.setChecked(routine.getDays().get(2));
        holder.wednesdayToggle.setChecked(routine.getDays().get(3));
        holder.thursdayToggle.setChecked(routine.getDays().get(4));
        holder.fridayToggle.setChecked(routine.getDays().get(5));
        holder.saturdayToggle.setChecked(routine.getDays().get(6));

        Toast.makeText(holder.itemView.getContext(), "" + routine.getDays().get(6), Toast.LENGTH_SHORT).show();

        holder.routineName.setText(routine.getName());

        holder.routineTime.setText(routine.getRoutineTime());


        holder.routineElementFirstLightColor.setBackgroundColor(routine.getLightsLight().get(0).getConvertedColor());
        holder.routineElementSecondLightColor.setBackgroundColor(routine.getLightsLight().get(1).getConvertedColor());
        holder.routineElementThirdLightColor.setBackgroundColor(routine.getLightsLight().get(2).getConvertedColor());

    }

    @Override
    public int getItemCount() {
        return routineList.size();
    }


    class RoutineHolder extends RecyclerView.ViewHolder {



        private TextView routineName;
        private TextView routineTime;

        private ToggleButton sundayToggle;
        private ToggleButton mondayToggle;
        private ToggleButton tuesdayToggle;
        private ToggleButton wednesdayToggle;
        private ToggleButton thursdayToggle;
        private ToggleButton fridayToggle;
        private ToggleButton saturdayToggle;

        private ImageView routineElementFirstLightColor;
        private ImageView routineElementSecondLightColor;
        private ImageView routineElementThirdLightColor;



        public RoutineHolder(View itemView) {
            super(itemView);

            routineName = (TextView) itemView.findViewById(R.id.routineElementName);

            routineTime = (TextView) itemView.findViewById(R.id.routineElementTime);

            sundayToggle = (ToggleButton) itemView.findViewById(R.id.sundayToggle);
            mondayToggle = (ToggleButton) itemView.findViewById(R.id.mondayToggle);
            tuesdayToggle = (ToggleButton) itemView.findViewById(R.id.tuesdayToggle);
            wednesdayToggle = (ToggleButton) itemView.findViewById(R.id.wednesdayToggle);
            thursdayToggle = (ToggleButton) itemView.findViewById(R.id.thursdayToggle);
            fridayToggle = (ToggleButton) itemView.findViewById(R.id.fridayToggle);
            saturdayToggle = (ToggleButton) itemView.findViewById(R.id.saturdayToggle);

            routineElementFirstLightColor = (ImageView) itemView.findViewById(R.id.routineElementFirstLightColor);
            routineElementSecondLightColor = (ImageView) itemView.findViewById(R.id.routineElementSecondLightColor);
            routineElementThirdLightColor = (ImageView) itemView.findViewById(R.id.routineElementThirdLightColor);


        }
    }

}
