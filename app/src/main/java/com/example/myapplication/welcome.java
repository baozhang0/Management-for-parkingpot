package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final Intent it=new Intent(this,LoginActivity.class);
        Timer timer =new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                startActivity(it);
            }
        };

        timer.schedule(task,3000);




    }
}
