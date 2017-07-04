package com.example.seniordesign.smartlighthub.Controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * Created by jamesharrison on 5/26/17.
 */

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.LightsHolder>{


    private List<Light> lightsList;

    private LayoutInflater inflater;

    public HomePageAdapter(List<Light> lightsList, Context c)
    {
        this.inflater = LayoutInflater.from(c);

        this.lightsList = lightsList;
    }


    @Override
    public LightsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.homepage_light_row, parent, false);

        return new LightsHolder(view);
    }

    @Override
    public void onBindViewHolder(LightsHolder holder, int position) {

        Light light = lightsList.get(position);

        holder.lightName.setText(light.getName());

        holder.lightState.setChecked(light.isState());

        holder.homepageCardView.setBackgroundColor(light.getConvertedColor());
    }

    @Override
    public int getItemCount() {


        return lightsList.size();
    }

    class LightsHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {

        private TextView lightName;
        private Switch lightState;
        private LinearLayout lightContainer;
        private CardView homepageCardView;




        public LightsHolder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            homepageCardView = (CardView) itemView.findViewById(R.id.homepageCardView);

            lightContainer = (LinearLayout) itemView.findViewById(R.id.lightsContainer);

            lightName = (TextView) itemView.findViewById(R.id.firstLightName);

            lightState = (Switch) itemView.findViewById(R.id.firstLightState);


        }


        @Override
        public void onClick(View v) {

            final int initialColor = ((ColorDrawable) homepageCardView.getBackground()).getColor();
            openColorPickerDialog(false, initialColor);

        }


        @Override
        public boolean onLongClick(View v) {

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

            Log.d("HomePageAdapter", "Clicked " + getAdapterPosition());

            Toast.makeText(v.getContext(), "we clickiy", Toast.LENGTH_SHORT).show();

            return true;
        }



        // Color picker 2
        private void openColorPickerDialog(boolean AlphaSupport, final int initialColor) {

            AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(itemView.getContext(), initialColor, AlphaSupport, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onOk(AmbilWarnaDialog ambilWarnaDialog, int color) {

                    homepageCardView.setBackgroundColor(color);

                    updateDatabaseLightColor(getAdapterPosition());

                    JSONObject rbgObject = new JSONObject();


                    int red = Color.red(color);
                    int green = Color.green(color);
                    int blue = Color.blue(color);


                    try {
                        rbgObject.put("0", red);
                        rbgObject.put("1", green);
                        rbgObject.put("2", blue);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    pubnubConfig(rbgObject, getAdapterPosition());


                }

                @Override
                public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {

                    Toast.makeText(itemView.getContext(), "Color Picker Closed", Toast.LENGTH_SHORT).show();
                }
            });
            ambilWarnaDialog.show();

        }



        public boolean updateDatabaseLightColor(final int lightNumber) {



            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

            final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(currentUser.getUid()).child("Lights");

            DatabaseReference lightRef = userRef.child("Light " + (lightNumber + 1));

            Drawable lightDrawableColor = (Drawable) homepageCardView.getBackground();

            int colorInt = ((ColorDrawable) lightDrawableColor).getColor();

            int red = Color.red(colorInt);
            int green = Color.green(colorInt);
            int blue = Color.blue(colorInt);

            String RGBcolor = red + ", " + green + ", " + blue;

            lightRef.child("Color").setValue(RGBcolor);

            lightsList.clear();

            return true;
        }

    }


    public void pubnubConfig(final JSONObject pubnubObject, int position) {
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey("sub-c-40e3d906-4ee7-11e7-bf50-02ee2ddab7fe");
        pnConfiguration.setPublishKey("pub-c-6528095d-bc26-4768-a903-ac0a85174f81");
        pnConfiguration.setSecure(false);

        PubNub pubnub = new PubNub(pnConfiguration);


        switch (position) {
            case 0:
                pubnub.publish().message(pubnubObject).channel("Light_1")
                        .async(new PNCallback<PNPublishResult>() {
                            @Override
                            public void onResponse(PNPublishResult result, PNStatus status) {
                                Log.d("HomePage Adapter ", "Light 1 publish: " + pubnubObject);
                            }
                        });

                break;

            case 1:

                pubnub.publish().message(pubnubObject).channel("Light_2")
                        .async(new PNCallback<PNPublishResult>() {
                            @Override
                            public void onResponse(PNPublishResult result, PNStatus status) {
                                Log.d("HomePage Adapter ", "Light 2 publish: " + pubnubObject);
                            }
                        });

                break;

            case 2:

                pubnub.publish().message(pubnubObject).channel("Light_3")
                        .async(new PNCallback<PNPublishResult>() {
                            @Override
                            public void onResponse(PNPublishResult result, PNStatus status) {
                                Log.d("HomePage Adapter ", "Light 3 publish: " + pubnubObject);
                            }
                        });

                break;

        }

    }






}
