package com.example.fyp_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class VolumeTunning extends AppCompatActivity {
    AudioManager audioManager;
    TextToSpeech test;
    TextView prog;
    SeekBar seekBar;
    boolean pass = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_tunning);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setMax(maxVolume);
        seekBar.setProgress(currentVolume);
        prog = (TextView) findViewById(R.id.progress);
        prog.setText("" + audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) + " %");


        test = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {            // Create Speech
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    test.setLanguage(Locale.UK);
                    Log.v("Speech", "success");
                }
            }
        });
        if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) >= 70) {
            new CountDownTimer(3000, 1000) {

                public void onTick(long millisUntilFinished) {
                    if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) < 70) {
                        pass = false;
                    } else {
                        pass = true;
                    }
                }

                public void onFinish() {
                    if (pass) {
                        Intent intent = new Intent(getApplicationContext(), TiltingActivity.class);
                        startActivity(intent);
                    }
                }

            }.start();
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                test.speak("testing speak", TextToSpeech.QUEUE_FLUSH, null, null);
                prog.setText("" + audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) + " %");
                if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) >= 70) {
                    new CountDownTimer(3000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) < 70) {
                                pass = false;
                            } else {
                                pass = true;
                            }
                        }

                        public void onFinish() {
                            if (pass) {
                                Intent intent = new Intent(getApplicationContext(), TiltingActivity.class);
                                startActivity(intent);
                            }
                        }

                    }.start();
                }
            }

            @Override

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action, keycode;

        action = event.getAction();
        keycode = event.getKeyCode();

        switch (keycode) {
            case KeyEvent.KEYCODE_VOLUME_UP: {
                if (KeyEvent.ACTION_UP == action) {
                    int current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, current + 1, 0);
                    test.speak("testing speak", TextToSpeech.QUEUE_FLUSH, null, null);
                    seekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
                    prog.setText("" + audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) + " %");
                    if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) >= 70) {
                        new CountDownTimer(3000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) < 70) {
                                    pass = false;
                                } else {
                                    pass = true;
                                }
                            }

                            public void onFinish() {
                                if (pass) {
                                    Intent intent = new Intent(getApplicationContext(), TiltingActivity.class);
                                    startActivity(intent);
                                }
                            }

                        }.start();
                    }
                }
                break;
            }
            case KeyEvent.KEYCODE_VOLUME_DOWN: {
                if (KeyEvent.ACTION_DOWN == action) {
                    int current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, current - 1, 0);
                    test.speak("testing speak", TextToSpeech.QUEUE_FLUSH, null, null);
                    seekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
                    prog.setText("" + audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
                    if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) >= 70) {
                        new CountDownTimer(3000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) < 70) {
                                    pass = false;
                                } else {
                                    pass = true;
                                }
                            }

                            public void onFinish() {
                                if (pass) {
                                    Intent intent = new Intent(getApplicationContext(), TiltingActivity.class);
                                    startActivity(intent);
                                }
                            }

                        }.start();
                    }
                }
                break;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}