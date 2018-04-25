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

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView kompassPic;
    TextView suund;
    private float algsedKraadid = 0f;

    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kompassPic = findViewById(R.id.kompassPic);
        suund = findViewById(R.id.nurk);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

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
