/*
 * Copyright © 11/16/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.pexers.ojornallusitano.databinding.ActivityMainBinding
import com.pexers.ojornallusitano.util.SharedPreferencesData


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferencesData.init(applicationContext)  // Load shared preferences
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.toolbarMain.toolbarMain
        setSupportActionBar(toolbar)
        initNavToggle(toolbar)
    }

    private fun initNavToggle(toolbar: Toolbar) {
        ActionBarDrawerToggle(
            this, binding.drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close
        ).syncState()
    }
}