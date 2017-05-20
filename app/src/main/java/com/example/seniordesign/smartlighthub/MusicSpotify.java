package com.example.seniordesign.smartlighthub;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Connectivity;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;
import com.spotify.sdk.android.player.PlayerEvent;

import java.util.List;

public class MusicSpotify extends AppCompatActivity implements ConnectionStateCallback, Player.NotificationCallback, View.OnClickListener
{

    /**
     Constant Section
     **/



    private static final String CLIENT_ID = "eed47028bf274baba3e689d68fb1a4ca";

    private static final String REDIRECT_URI = "seniordesign://callback";

    private static final int REQUEST_CODE = 8763;

    private static final String TAG = "MusicSpotify";

    private Player mPlayer;

    private Button play;

    private Button pause;

    private Button resume;

    private ListView trackList;

    String [] songList = {"Despacito" , "Sacrifices" , "Passionfruit", "HUMBLE", "No Role Modelz", "Neighbors"};

    String[] songsURIList = {"spotify:track:7CUYHcu0RnbOnMz4RuN07w", "spotify:track:0XpEoWpZqlQpGFYZXDU2Hj",
            "spotify:track:5mCPDVBb16L4XQwDdbRUpz", "spotify:track:7KXjTSCq5nL1LoYtL7XAwS",
            "spotify:track:62vpWI1CHwFy7tMIcSStl8", "spotify:track:0utlOiJy2weVl9WTkcEWHy"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_spotify);

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);

        init();
    }

    public void init()
    {
        play = (Button) findViewById(R.id.playButton);
        play.setOnClickListener(this);

        pause = (Button) findViewById(R.id.pauseButton);
        pause.setOnClickListener(this);

        resume = (Button) findViewById(R.id.resumeButton);
        resume.setOnClickListener(this);
        resume.setVisibility(View.INVISIBLE);

        ListAdapter songsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songList);

        trackList = (ListView) findViewById(R.id.trackList);

        trackList.setAdapter(songsAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(MusicSpotify.this);
                        mPlayer.addNotificationCallback(MusicSpotify.this);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e(TAG, "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        // VERY IMPORTANT! This must always be called or else you will leak resources
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d(TAG, "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }

    }
    @Override
    public void onPlaybackError(Error error) {
        Log.d(TAG, "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d(TAG, "User logged in");


//        mPlayer.playUri(null, "spotify:track:0XpEoWpZqlQpGFYZXDU2Hj", 0, 0);
    }
    @Override
    public void onLoggedOut() {
        Log.d(TAG, "User logged out");
    }

    @Override
    public void onLoginFailed(Error error ){
        Log.d(TAG, "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d(TAG, "Temporary error occurred");
    }


    @Override
    public void onConnectionMessage(String message) {
        Log.d(TAG, "Received connection message: " + message);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.playButton:
                mPlayer.playUri(null, "spotify:track:0XpEoWpZqlQpGFYZXDU2Hj", 0, 0);
                break;

            case R.id.pauseButton:
                mPlayer.pause(null);
                play.setVisibility(View.INVISIBLE);
                pause.setVisibility(View.INVISIBLE);
                resume.setVisibility(View.VISIBLE);
                break;

            case R.id.resumeButton:
                mPlayer.resume(null);
                play.setVisibility(View.VISIBLE);
                pause.setVisibility(View.VISIBLE);
                resume.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
