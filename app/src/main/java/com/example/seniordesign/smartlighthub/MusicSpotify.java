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

public class MusicSpotify extends AppCompatActivity implements ConnectionStateCallback,  Player.NotificationCallback
{

    /**

    Constant Section

     **/


    //
    private static final String CLIENT_ID = "eed47028bf274baba3e689d68fb1a4ca";

    private static final String REDIRECT_URI = "seniordesign://callback";

    private static final int REQUEST_CODE = 8763;

    public static final String TAG = "SmartLightHub";
    private Player mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_spotify);

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
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
                        Log.e("MusicSpotify", "Could not initialize player: " + throwable.getMessage());
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
        Log.d("MusicSpotify", "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }

    }
    @Override
    public void onPlaybackError(Error error) {
        Log.d("MusicSpotify", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("MusicSpotify", "User logged in");

        mPlayer.playUri(null, "spotify:track:0XpEoWpZqlQpGFYZXDU2Hj", 0, 0);
    }
    @Override
    public void onLoggedOut() {
        Log.d("MusicSpotify", "User logged out");
    }

    @Override
    public void onLoginFailed(Error error ){
        Log.d("MusicSpotify", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("MusicSpotify", "Temporary error occurred");
    }


    @Override
    public void onConnectionMessage(String message) {
        Log.d("MusicSpotify", "Received connection message: " + message);
    }


}
