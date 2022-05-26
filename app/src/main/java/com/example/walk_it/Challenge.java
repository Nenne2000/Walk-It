package com.example.walk_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Challenge extends AppCompatActivity implements SensorEventListener {

    private long steps=0;
    private final String TAG = "Challenge";
    private TextView tvNomeSfida=null;
    private TextView tvNumPassi= null;

    private TextView countDownText= null;
    private Button startButton = null;

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 600000; //10 min
    private boolean timeIsRunning;

    private SensorManager sensorManager = null;
    private Sensor counterStep = null, detectorStep = null;
    private SensorEventListener sensorEventListener = null;
    int stepCount = 0, stepDetect=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        //timer
        countDownText = findViewById(R.id.countDown);
        startButton = findViewById(R.id.start);

        tvNomeSfida=findViewById(R.id.tvNomeSfida);
        tvNumPassi=findViewById(R.id.tvNumPassi);

        sensorEventListener = this;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //se il sensore esiste sul telefonno dove gira l'app allora viene recuperato
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            counterStep = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            Log.i(TAG, "Sensore stepCounter inizializzato correttamente");
        } else {
            Log.i(TAG, "Sensore stepCounter non presente");
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            detectorStep = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            Log.i(TAG, "Sensore StepDetector inizializzato correttamente");
        } else {
            Log.i(TAG, "Sensore StepDetector non presente");
        }

        Intent _intent=getIntent();
        String _nomeSfida=_intent.getStringExtra("NOME_SFIDA");
        tvNomeSfida.setText(_nomeSfida);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop();

            }
        });
    }

    private void startStop() {
        if(timeIsRunning) stopTimer();
        else startTimer();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
        startButton.setText("PAUSE");
        timeIsRunning = true;
    }

    private void stopTimer() {
        countDownTimer.cancel();
        startButton.setText("START");
        timeIsRunning = false;
    }

    private void updateTimer() {
        int minute = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        String timeLeft;
        timeLeft = "" + minute;
        timeLeft += ":";
        if(seconds < 10) timeLeft += "0";
        timeLeft += seconds;

        countDownText.setText(timeLeft);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.i(TAG, "OnSensorChanged");
        if (sensorEvent.sensor == counterStep) {
            stepCount = (int) sensorEvent.values[0];
            tvNumPassi.setText(String.valueOf(stepCount));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i(TAG, "Accuracy changed, New accuracy: " + accuracy);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            sensorManager.registerListener(this, counterStep, SensorManager.SENSOR_DELAY_FASTEST);
        }
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)!=null)
            sensorManager.registerListener(this,detectorStep,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            sensorManager.unregisterListener(this,counterStep);
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            sensorManager.unregisterListener(this,detectorStep);
        }
    }

}