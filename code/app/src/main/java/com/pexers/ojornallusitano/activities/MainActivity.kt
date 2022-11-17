/*
 * Copyright © 11/17/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.activities

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.pexers.ojornallusitano.R
import com.pexers.ojornallusitano.databinding.ActivityMainBinding
import com.pexers.ojornallusitano.fragments.CategoriesFragment
import com.pexers.ojornallusitano.utils.SharedPreferencesData


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferencesData.init(applicationContext)  // Load shared preferences
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.toolbarMain.toolbarMain
        setSupportActionBar(toolbar)
        setupNav(toolbar)

    }

    private fun setupNav(toolbar: Toolbar) {
        // Init nav toggle
        ActionBarDrawerToggle(
            this, binding.drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close
        ).syncState()
        // Setup on item selected listener
        val drawerLayout = binding.drawerLayout
        val categoriesFragment = CategoriesFragment()

        setAllFragment(categoriesFragment) // Initial fragment
        binding.navigation.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_all -> {
                    setAllFragment(categoriesFragment)
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    true
                }
                R.id.item_favourites -> {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    true
                }
                R.id.item_recent -> {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    true
                }
                R.id.item_about -> {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    true
                }
                else -> {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    false
                }
            }

        }
    }

    private fun setAllFragment(categoriesFragment: CategoriesFragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout_main, categoriesFragment)
            commit()
        }
    }
}