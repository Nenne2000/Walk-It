package com.example.walk_it;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class MyForegroundService extends Service implements SensorEventListener{


    private SensorManager sensorManager = null;
    private final String TAG = "Foreground_service";
    private Sensor counterStep = null;
    private int stepcount;
    private SensorEventListener sensorEventListener = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    Log.e("Service", "Service is running...");
                    try{
                        Thread.sleep(2000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
*/
        /*
        Context context = getApplicationContext();
        Intent notificationIntent = new Intent(this, ExampleActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent,
                        PendingIntent.FLAG_IMMUTABLE);

        final String CHANNELID = "WALK_IT FOREGROUND SERVICE";
        NotificationChannel channel = new NotificationChannel(
                CHANNELID,
                CHANNELID,
                NotificationManager.IMPORTANCE_LOW
        );
        Log.i(TAG, "passi: " + stepcount);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNELID)
                .setContentText("Oggi hai gia' fatto " + stepcount + " passi")
                .setContentTitle("WALK-IT!")
                .setSmallIcon(R.drawable.ic_launcher_background);
        startForeground(1001, notification.build());
        */

        sensorEventListener = this;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //se il sensore esiste sul telefono dove gira l'app allora viene recuperato
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            counterStep = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            Log.i(TAG, "Sensore StepCounter inizializzato correttamente");
        } else {
            Log.i(TAG, "Sensore StepCounter non presente");
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            sensorManager.registerListener(this, counterStep, SensorManager.SENSOR_DELAY_FASTEST);
            Log.i(TAG, "sensore registrato");
        }
/*
        final String CHANNELID = "WALK_IT FOREGROUND SERVICE";
        NotificationChannel channel = new NotificationChannel(
                CHANNELID,
                CHANNELID,
                NotificationManager.IMPORTANCE_LOW
        );

        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNELID)
                .setContentText("Oggi hai gia' fatto " + stepcount + " passi")
                .setContentTitle("WALK-IT!")
                .setSmallIcon(R.drawable.ic_launcher_background);
        startForeground(1001, notification.build());*/
        return super.onStartCommand(intent,flags,startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor == counterStep) {
            Log.i(TAG, "onsensorChange");
            stepcount = (int) sensorEvent.values[0];
            Log.i(TAG, "passi: " + stepcount);
            final String CHANNELID = "WALK_IT FOREGROUND SERVICE";
            NotificationChannel channel = new NotificationChannel(
                    CHANNELID,
                    CHANNELID,
                    NotificationManager.IMPORTANCE_LOW
            );

            getSystemService(NotificationManager.class).createNotificationChannel(channel);
            Notification.Builder notification = new Notification.Builder(this, CHANNELID)
                    .setContentText("Oggi hai gia' fatto " + stepcount + " passi, il tuo obbiettivo è 1000!")
                    .setContentTitle("Continua a camminare!")
                    .setSmallIcon(R.drawable.ic_launcher_foreground);
            startForeground(1001, notification.build());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i(TAG, "Accuracy changed, New accuracy: " + accuracy);
    }
}
