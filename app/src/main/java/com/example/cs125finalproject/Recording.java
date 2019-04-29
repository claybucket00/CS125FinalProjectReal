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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.IOException;

public class Recording extends AppCompatActivity {
    private static final String LOG_TAG = "Music";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;
    boolean recording1 = true;
    boolean recording2 = true;
    boolean recording3 = true;
    boolean recording4 = true;
    boolean playing1 = true;
    boolean playing2 = true;
    boolean playing3 = true;
    boolean playing4 = true;

    private MediaRecorder recorder = null;
    private MediaPlayer player = null;

    //Requesting permission to Record Audio
    private boolean permissionToRecordTrue = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
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
            player.setLooping(true);
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
        super.onCreate(savedInstanceState);
        //record file to external cache
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/music.3gp";
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);


        setContentView(R.layout.activity_recording);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ToggleButton record1 = findViewById(R.id.recordButton1);
        record1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onRecord(recording1);
                recording1 = !recording1;
            }
        });

        final ToggleButton record2 = findViewById(R.id.recordButton2);
        record2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onRecord(recording2);
                recording2 = !recording2;
            }
        });

        final ToggleButton record3 = findViewById(R.id.recordButton3);
        record3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onRecord(recording3);
                recording3 = !recording3;
            }
        });

        final ToggleButton record4 = findViewById(R.id.recordButton4);
        record4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onRecord(recording4);
                recording4 = !recording4;
            }
        });

        final Switch play1 = findViewById(R.id.playSwitch1);
        play1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onPlay(playing1);
                playing1 = !playing1;
            }
        });

        final Switch play2 = findViewById(R.id.playSwitch2);
        play2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onPlay(playing2);
                playing2 = !playing2;
            }
        });

        final Switch play3 = findViewById(R.id.playSwitch3);
        play3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onPlay(playing3);
                playing3 = !playing3;
            }
        });

        final Switch play4 = findViewById(R.id.playSwitch4);
        play4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onPlay(playing4);
                playing4 = !playing4;
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
