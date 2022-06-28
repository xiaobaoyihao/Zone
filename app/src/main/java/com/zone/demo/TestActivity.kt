package com.zone.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zone.demo.databinding.ActivityTestBinding

class TestActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}