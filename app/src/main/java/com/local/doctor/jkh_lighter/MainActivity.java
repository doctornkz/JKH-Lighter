package com.local.doctor.jkh_lighter;

//TODO: Add Local Storage for Temporary Measurements.
//TODO: Add Settings for Sections - Energy, Water, Gas, Custom Fields.

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private Camera camera;
    private EditText t1;
    private EditText t2;
    private EditText t3;
    private EditText cold;
    private EditText hot;
    private String TAG = "Meters";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = this;

        PackageManager pm = context.getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Log.e(TAG, "Device has no camera!");
            return;
        }

        Switch switch_light = (Switch)findViewById(R.id.LightSwitch);
        switch_light.setChecked(false);
        Log.d(TAG, "Switch initialize");

        camera = Camera.open();
        Log.d(TAG, "Camera initialize");

        t1 = (EditText) findViewById(R.id.editTextT1);
        t2 = (EditText) findViewById(R.id.editTextT2);
        t3 = (EditText) findViewById(R.id.editTextT3);
        cold = (EditText) findViewById(R.id.editTextCold);
        hot = (EditText) findViewById(R.id.editTextHot);

        switch_light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                if(isChecked){
                    if (camera == null) {
                        Log.d(TAG, "Check = true, Camera service gone away...Restart!");
                        camera = Camera.open();
                    }
                    final Parameters p = camera.getParameters();
                    p.setFlashMode(Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(p);
                    camera.startPreview();
                    Log.d(TAG, "Camera switched ON by switch");
                } else {
                    Log.d(TAG, "Else statement!");
                    if (camera == null) {
                        Log.d(TAG, "Check = false, Camera service gone away...");
                    }
                    final Parameters p = camera.getParameters();
                    p.setFlashMode(Parameters.FLASH_MODE_OFF);
                    camera.setParameters(p);
                    camera.stopPreview();
                    if (camera != null) {
                        camera.release();
                        camera = null;
                        Log.d(TAG, "Camera released in switch");
                    }
                    Log.d(TAG, "Camera switched OFF by switch");
            }
        }
        });

        Log.d(TAG, "Outbound from switch...");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        Log.d(TAG, "Menu initialize");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final String energy = "Energy: T1:" + t1.getText().toString() + " T2:" + t2.getText().toString() + " T3:" + t3.getText().toString();
        final String water = "Water: Cold:" + cold.getText().toString() + " Hot:" + hot.getText().toString();
        String message = energy + "\n" + water;
        int id = item.getItemId();
        Log.d(TAG, "Menu item Share pressed.");
        switch (id) {
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Utilities: " + "\n" + message);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                Log.d(TAG, "Sending utilities info...");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
