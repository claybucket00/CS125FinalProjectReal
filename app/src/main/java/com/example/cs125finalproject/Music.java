package com.example.cs125finalproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;
/**
 * Uses Android media player API to record audio clips and play them back.
 */
public class Music extends AppCompatActivity {
    private static final String LOG_TAG = "Music";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;

    private MediaRecorder recorder = null;
    private MediaPlayer player = null;

    //Requesting permission to Record Audio
    private boolean permissionToRecordTrue = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

//    @Override
//    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull
//                                          super.onRequestPermissionResult(requestCode, permissions, grantResults);
//    switch (requestCode) {
//        case REQUEST_RECORD_AUDIO_PERMISSION;
//            permissionToRecordTrue = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//            break;
//    }
//    if (!permissionToRecordTrue) finish();

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
    public void onCreate(Bundle reee) {
        super.onCreate(reee);

        //record file to external cache
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/music.3gp";
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
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
}
