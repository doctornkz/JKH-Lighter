package com.local.doctor.jkh_lighter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();

        final TextView textViewT1, textViewT2, textViewT3, textViewCold, textViewHot;
        final Button btnCancel;
        final Button btnSend;

        textViewT1 = (TextView) findViewById(R.id.textViewT1);
        textViewT2 = (TextView) findViewById(R.id.textViewT2);
        textViewT3 = (TextView) findViewById(R.id.textViewT3);
        textViewCold = (TextView) findViewById(R.id.textViewCold);
        textViewHot = (TextView) findViewById(R.id.textViewHot);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSend = (Button) findViewById(R.id.btnSend);
        String t1 = intent.getStringExtra("t1");
        String t2 = intent.getStringExtra("t2");
        String t3 = intent.getStringExtra("t3");
        String cold = intent.getStringExtra("cold");
        String hot = intent.getStringExtra("hot");
        textViewT1.setText(t1);
        textViewT2.setText(t2);
        textViewT3.setText(t3);
        textViewCold.setText(cold);
        textViewHot.setText(hot);

        final String energy = "Energy: T1:" + t1 + " T2:" + t2 + " T3:" + t3;
        final String water = "Water: Cold:" + cold + " Hot:" + hot;



         View.OnClickListener oclBtnSend = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = energy + "\n" + water;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Utilities: " + "\n" + message);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        };


        btnSend.setOnClickListener(oclBtnSend);


        View.OnClickListener oclBtnCancel = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        };
        btnCancel.setOnClickListener(oclBtnCancel);

    }
}
