package com.example.cs125finalproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.media.MediaMuxer;
import android.media.SoundPool;

import java.io.IOException;

public class Recording extends AppCompatActivity {
    private static final String LOG_TAG = "Music";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;
    private static String downloadFile = null;
    boolean recording1 = true;
    boolean recording2 = true;
    boolean recording3 = true;
    boolean recording4 = true;
    boolean playing1 = true;
    boolean playing2 = true;
    boolean playing3 = true;
    boolean playing4 = true;
    private boolean mixing = true;
    private int first = 0;
    private int second = 0;
    private int third = 0;
    private int fourth = 0;

    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    private SoundPool pool = null;

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
        player.setLooping(false);
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
        if (pool != null) {
            pool.release();
            pool = null;
        }
    }
    private void mixing() {
        if (mixing) {
            pool = new SoundPool(4, 2, 3);
            String file1 = getExternalCacheDir().getAbsolutePath();
            file1 += "/music.3gp";
            String file2 = getExternalCacheDir().getAbsolutePath();
            file2 += "/music1.3gp";
            String file3 = getExternalCacheDir().getAbsolutePath();
            file3 += "/music2.3gp";
            String file4 = getExternalCacheDir().getAbsolutePath();
            file4 += "/music3.3gp";
            first = pool.load(file1, 1);
            second = pool.load(file2, 2);
            third = pool.load(file3, 3);
            fourth = pool.load(file4, 4);
            mixing = false;
        } else {
            pool.release();
            pool = new SoundPool(4, 2, 3);
            String file1 = getExternalCacheDir().getAbsolutePath();
            file1 += "/music.3gp";
            String file2 = getExternalCacheDir().getAbsolutePath();
            file2 += "/music1.3gp";
            String file3 = getExternalCacheDir().getAbsolutePath();
            file3 += "/music2.3gp";
            String file4 = getExternalCacheDir().getAbsolutePath();
            file4 += "/music3.3gp";
            first = pool.load(file1, 1);
            second = pool.load(file2, 2);
            third = pool.load(file3, 3);
            fourth = pool.load(file4, 4);
        }
    }
    private void onMix(boolean isMixing) {
        if (isMixing) {
            startMixing();
        } else {
            stopMixing();
        }
    }
    private void startMixing() {
        pool.play(first, 0.75f, 1, 1, -1, 1);
        pool.play(second, 1, 0.75f, 1, -1, 1);
        pool.play(third, 0.50f, 0.75f, 1, -1, 1);
        pool.play(fourth, 0.75f, 0.50f, 1, -1, 1);
    }
    private void stopMixing() {
        pool.stop(first);
        pool.stop(second);
        pool.stop(third);
        pool.stop(fourth);
    }
    private void changePitch(int track) {
        if (track == first && first != 0) {
            pool.pause(first);
            if (true) {
                pool.setRate(first, 2);
                pool.resume(first);
            } else if (true) {
                pool.setRate(first, 0.5f);
                pool.resume(first);
            } else {
                pool.setRate(first, 1f);
                pool.resume(first);
            }
        }
        if (track == second && second != 0) {
            pool.pause(second);
            if (true) {
                pool.setRate(second, 2);
                pool.resume(second);
            } else if (true) {
                pool.setRate(second, 0.5f);
                pool.resume(second);
            } else {
                pool.setRate(second, 1f);
                pool.resume(second);
            }
        }
        if (track == third && third != 0) {
            pool.pause(third);
            if (true) {
                pool.setRate(third, 2);
                pool.resume(third);
            } else if (true) {
                pool.setRate(third, 0.5f);
                pool.resume(third);
            } else {
                pool.setRate(third, 1f);
                pool.resume(third);
            }
        }
        if (track == fourth && fourth != 0) {
            pool.pause(fourth);
            if (true) {
                pool.setRate(fourth, 2);
                pool.resume(fourth);
            } else if (true) {
                pool.setRate(fourth, 0.5f);
                pool.resume(fourth);
            } else {
                pool.setRate(fourth, 1f);
                pool.resume(fourth);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //record file to external cache
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);


        setContentView(R.layout.activity_recording);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ToggleButton record1 = findViewById(R.id.recordButton1);
        record1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (recording2 && recording3 && recording4) {
                    fileName = getExternalCacheDir().getAbsolutePath();
                    fileName += "/music.3gp";
                    onRecord(recording1);
                    recording1 = !recording1;
                } else {
                    record1.setSelected(false);
                }
            }
        });

        final ToggleButton record2 = findViewById(R.id.recordButton2);
        record2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (recording1 && recording3 && recording4) {
                    fileName = getExternalCacheDir().getAbsolutePath();
                    fileName += "/music1.3gp";
                    onRecord(recording2);
                    recording2 = !recording2;
                } else {
                    record2.setSelected(false);
                }
            }
        });

        final ToggleButton record3 = findViewById(R.id.recordButton3);
        record3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (recording1 && recording2 && recording4) {
                    fileName = getExternalCacheDir().getAbsolutePath();
                    fileName += "/music2.3gp";
                    onRecord(recording3);
                    recording3 = !recording3;
                } else {
                    record3.setSelected(false);
                }
            }
        });

        final ToggleButton record4 = findViewById(R.id.recordButton4);
        record4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (recording1 && recording2 && recording3) {
                    fileName = getExternalCacheDir().getAbsolutePath();
                    fileName += "/music3.3gp";
                    onRecord(recording4);
                    recording4 = !recording4;
                } else {
                    record4.setSelected(false);
                }
            }
        });

        final Switch play1 = findViewById(R.id.playSwitch1);
        play1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fileName = getExternalCacheDir().getAbsolutePath();
                fileName += "/music.3gp";
                onPlay(playing1);
                playing1 = !playing1;
            }
        });

        final Switch play2 = findViewById(R.id.playSwitch2);
        play2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fileName = getExternalCacheDir().getAbsolutePath();
                fileName += "/music1.3gp";
                onPlay(playing2);
                playing2 = !playing2;
            }
        });

        final Switch play3 = findViewById(R.id.playSwitch3);
        play3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fileName = getExternalCacheDir().getAbsolutePath();
                fileName += "/music2.3gp";
                onPlay(playing3);
                playing3 = !playing3;
            }
        });

        final Switch play4 = findViewById(R.id.playSwitch4);
        play4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fileName = getExternalCacheDir().getAbsolutePath();
                fileName += "/music3.3gp";
                onPlay(playing4);
                playing4 = !playing4;
            }
        });
    }
}
