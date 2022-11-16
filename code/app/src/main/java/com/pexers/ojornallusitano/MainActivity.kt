/*
 * Copyright © 11/16/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pexers.ojornallusitano.databinding.ActivityMainBinding
import com.pexers.ojornallusitano.util.SharedPreferencesData


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferencesData.init(applicationContext)  // Load shared preferences
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar_main))
    }
}