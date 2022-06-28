package com.zone.demo;

import static com.zone.runtime.ConfigKt.TAG_ZONE;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.zone.demo.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d(TAG_ZONE, "main activity");
    }
}
