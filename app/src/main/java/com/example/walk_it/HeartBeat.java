package com.example.walk_it;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class HeartBeat extends AppCompatActivity implements SensorEventListener{
    public TextView testoLabel;
    private static final String TAG = "MyActivity";

    private int HEART_SENSOR_PERMISSION = 1;
    private long battiti = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate called");

        testoLabel = (TextView) findViewById(R.id.testoLabel);

        SensorManager sMgr;
        sMgr = (SensorManager)this.getSystemService(SENSOR_SERVICE);

        Sensor battito = null;
        battito = sMgr.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        if(battito != null)
            testoLabel.setText("load sensor");
        else
            testoLabel.setText("no load sensor");

        sMgr.registerListener(this, battito,SensorManager.SENSOR_DELAY_FASTEST);

        Button buttonRequest = findViewById(R.id.button);
        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(HeartBeat.this,
                        Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(HeartBeat.this, "You have already granted this permission!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    requestStoragePermission();
                }
            }
        });

    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.BODY_SENSORS)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(HeartBeat.this,
                                    new String[] {Manifest.permission.BODY_SENSORS}, HEART_SENSOR_PERMISSION);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.BODY_SENSORS}, HEART_SENSOR_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == HEART_SENSOR_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            battiti = (int)event.values[0];
            String msg = " Value sensor: " + battiti;
            Log.i(TAG, "battiti: " + battiti);
            testoLabel.setText(msg);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        System.out.println("onAccuracyChanged - accuracy: " + accuracy);
    }
}