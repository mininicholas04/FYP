package com.example.fyp_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TiltingActivity extends AppCompatActivity implements SensorEventListener{
    private static SensorManager sensorManager;
    private Sensor sensor;
    int height, width;
    TextView text ;
    View tilting ;
    ImageView circle;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private float[] mGravity;
    private float[] mGeomagnetic;
    private static final String TAG = "MainActivity";
    boolean pass = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tilting);
        text = findViewById(R.id.textView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorManager.registerListener(TiltingActivity.this,sensor,sensorManager.SENSOR_DELAY_FASTEST);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

       // DisplayMetrics displayMetrics = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
       // height = displayMetrics.heightPixels;
        //width = displayMetrics.widthPixels;

        Log.v("Height and Width",height+","+width);

        if (accelerometer == null) {
            Log.d(TAG, "accelerometer is null");
        }
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magnetometer == null) {
            Log.d(TAG, "magnetometer is null");
        }
        tilting = findViewById(R.id.tilting);
        new Thread(new Runnable(){
            public void run(){
                while(true){
                    if(pass){
                        Log.v("aaaaaaaaaaaaaa","passsssssssssssssss");
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(pass){
                            Log.v("aaaaaaaaaaaaaa","passsssssssssssssss");
                            startActivity(new Intent(TiltingActivity.this,Breath.class));
                            finish();
                            break;
                    }

                    }
                }
            }
        }).start();
    }

    protected void onResume() {
       super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_FASTEST);
    }


   protected void onPause() {
        super.onPause();
   }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values == null) {
            Log.w(TAG, "event.values is null");
            return;
        }
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                mGravity = event.values;
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mGeomagnetic = event.values;
                break;
            default:
                Log.w(TAG, "Unknown sensor type " + sensorType);
                return;
        }
        if (mGravity == null) {
            Log.w(TAG, "mGravity is null");
            return;
        }
        if (mGeomagnetic == null) {
            Log.w(TAG, "mGeomagnetic is null");
            return;
        }
        float[] R = new float[9];
        if (! SensorManager.getRotationMatrix(R, null, mGravity, mGeomagnetic)) {
            Log.w(TAG, "getRotationMatrix() failed");
            return;
        }

        float[] orientation = new float[9];
        SensorManager.getOrientation(R, orientation);
        // Orientation contains: azimuth, pitch and roll - we'll use roll
        float roll = orientation[2];
        int rollDeg = (int) Math.round(Math.toDegrees(roll));
        //Log.d(TAG, "deg=" + rollDeg + " power=" + power);
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(String.valueOf(rollDeg));
                    }
                });

            }
        }).start();

        if( -65>=rollDeg && rollDeg >= -71){
            tilting.setBackgroundColor(Color.GREEN);
            pass = true;
        }
        else{
            pass = false;
            tilting.setBackgroundColor(Color.RED);
        }
    }




    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}