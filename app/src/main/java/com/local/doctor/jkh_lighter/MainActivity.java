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

    public boolean existCamera = true;
    private boolean switcher = false;
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
            Log.d(TAG, "onStart:Device has no camera");
            existCamera = false;
            //return;
        }
    }

    public void SwitchCam(boolean switcher) {

        if (existCamera) {
            Log.d(TAG, "onStart:Cam Exist");
        }

        Switch switch_light = (Switch)findViewById(R.id.LightSwitch);
        switch_light.setChecked(switcher);

        if (switcher) {
            try {
                camera = Camera.open();
                final Parameters p = camera.getParameters();
                p.setFlashMode(Parameters.FLASH_MODE_TORCH);
                camera.setParameters(p);
                Log.d(TAG, "onStart:Camera switched ON by switch");
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.d(TAG, "onStart:Switch ON failed");
            }

        } else {
            try {
                //camera = Camera.open();
                final Parameters p = camera.getParameters();
                p.setFlashMode(Parameters.FLASH_MODE_OFF);
                camera.setParameters(p);
                Log.d(TAG, "onStart:Camera switched OFF by switch");

                camera.release();
                camera.unlock();

            } catch (Exception ex){
                ex.printStackTrace();
                Log.d(TAG, "onStart:Switch OFF failed");
                //TODO: Something wrong - always "failed" detected.
            }
        }
    }


    public void onStart()  {
        super.onStart();
        //SwitchCam(switcher, true);

// measurement =======================
        t1 = (EditText) findViewById(R.id.editTextT1);
        t2 = (EditText) findViewById(R.id.editTextT2);
        t3 = (EditText) findViewById(R.id.editTextT3);
        cold = (EditText) findViewById(R.id.editTextCold);
        hot = (EditText) findViewById(R.id.editTextHot);
// ===================================

        Switch switch_light = (Switch)findViewById(R.id.LightSwitch);
        switch_light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switcher = isChecked;
                SwitchCam(switcher);
        }
        });

        Log.d(TAG, "onStart:Outbound from switch.");

    }
// Menu ============================
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
    @Override
    public void onPause() {
        super.onPause();
        SwitchCam(false);
        Log.d(TAG, "onPause:");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        SwitchCam(false);
        Log.d(TAG, "onRestart:");
    }

    @Override
    public void onStop() {
        super.onStop();
        SwitchCam(false);
        Log.d(TAG, "onStop:");
    ////

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        SwitchCam(false);
        Log.d(TAG, "onDestroy:");


    }

}
