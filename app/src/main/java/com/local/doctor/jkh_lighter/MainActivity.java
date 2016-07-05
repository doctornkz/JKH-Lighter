package com.local.doctor.jkh_lighter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    //flag to detect flash is on or off
    private boolean isLightOn = false;
    private Camera camera;
    private EditText t1;
    private EditText t2;
    private EditText t3;
    private EditText cold;
    private EditText hot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = this;
        PackageManager pm = context.getPackageManager();

        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Log.e("err", "Device has no camera!");
            return;
        }

        camera = Camera.open();
        t1 = (EditText) findViewById(R.id.editTextT1);
        t2 = (EditText) findViewById(R.id.editTextT2);
        t3 = (EditText) findViewById(R.id.editTextT3);
        cold = (EditText) findViewById(R.id.editTextCold);
        hot = (EditText) findViewById(R.id.editTextHot);
        Button btnOk;
        btnOk = (Button) findViewById(R.id.btnOk);
        final Parameters p = camera.getParameters();

        if (isLightOn) {
            p.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(p);
            camera.stopPreview();
            isLightOn = false;

        } else {
            p.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(p);
            camera.startPreview();
            isLightOn = true;
        }


        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Parameters p = camera.getParameters();
                p.setFlashMode(Parameters.FLASH_MODE_OFF);
                camera.setParameters(p);
                camera.stopPreview();
                if (camera != null) {
                    camera.release();
                    camera = null;
                }

                isLightOn = false;

//TODO: Add string memory in two direction
//TODO: Kill Cam forcefully

                Intent intent = new Intent(v.getContext(), Main2Activity.class);
                intent.putExtra("t1", t1.getText().toString());
                intent.putExtra("t2", t2.getText().toString());
                intent.putExtra("t3", t3.getText().toString());
                intent.putExtra("cold", cold.getText().toString());
                intent.putExtra("hot", hot.getText().toString());
                startActivity(intent);
            }
        };
        btnOk.setOnClickListener(oclBtnOk);

    }
}
