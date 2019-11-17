package com.example.lifecycles;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    MyChronometer chronometer;
//    private long elapsedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometer = findViewById(R.id.meter);
        getLifecycle().addObserver(chronometer);
//        chronometer.setBase(System.currentTimeMillis());//UNIX 时间 1970-1-1
//        chronometer.setBase(SystemClock.elapsedRealtime());//系统启动后的时间
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        chronometer.setBase(SystemClock.elapsedRealtime() - elapsedTime);
//        chronometer.start();
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        elapsedTime = SystemClock.elapsedRealtime() - chronometer.getBase();
//        Log.d(TAG, "onPause: "+elapsedTime);
//        chronometer.stop();
//    }
}
