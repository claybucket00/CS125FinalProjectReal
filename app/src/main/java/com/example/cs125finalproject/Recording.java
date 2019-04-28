package com.example.cs125finalproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.IOException;

public class Recording extends AppCompatActivity {
    private static final String LOG_TAG = "Music";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;
    boolean recording = true;

    private MediaRecorder recorder = null;
    private MediaPlayer player = null;

    //Requesting permission to Record Audio
    private boolean permissionToRecordTrue = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopPlaying();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }
    private void stopPlaying() {
        player.release();
        player = null;
    }
    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //record file to external cache
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/music.3gp";
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ToggleButton record1 = findViewById(R.id.playButton1);
        record1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onRecord(recording);
                recording = !recording;
            }
        });


//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

//    @Override
//    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull
//                                          super.onRequestPermissionResult(requestCode, permissions, grantResults);
//    switch (requestCode) {
//        case REQUEST_RECORD_AUDIO_PERMISSION;
//            permissionToRecordTrue = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//            break;
//    }
//    if (!permissionToRecordTrue) finish();

}
