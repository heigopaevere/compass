package com.example.opilane.compass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView kompassPic;
    TextView suund, loe;
    private float algsedKraadid = 0f;

    private SensorManager sensorManager;
    boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kompassPic = findViewById(R.id.kompassPic);
        suund = findViewById(R.id.nurk);
        loe = (TextView) findViewById(R.id.loe);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null){
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this,"Sensor not found!", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;

        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running){
            loe.setText(String.valueOf(event.values[0]));
        }

        float kraadinurk = Math.round(event.values[0]);
        suund.setText("Suund: " + Float.toString(kraadinurk) + " kraadi");
        RotateAnimation poorlemisAnimatsioon = new RotateAnimation(algsedKraadid,-kraadinurk,
                Animation.RELATIVE_TO_SELF, 0.5F,Animation.RELATIVE_TO_SELF, 0.5f);

        poorlemisAnimatsioon.setDuration(200);
        poorlemisAnimatsioon.setFillAfter(true);
        kompassPic.startAnimation(poorlemisAnimatsioon);
        algsedKraadid =-kraadinurk;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
