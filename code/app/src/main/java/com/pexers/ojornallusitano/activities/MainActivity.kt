/*
 * Copyright © 11/18/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.activities

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pexers.ojornallusitano.R
import com.pexers.ojornallusitano.adapters.CategoriesAdapter
import com.pexers.ojornallusitano.databinding.ActivityMainBinding
import com.pexers.ojornallusitano.fragments.Categories
import com.pexers.ojornallusitano.fragments.CategoriesFragment
import com.pexers.ojornallusitano.fragments.FavouritesFragment
import com.pexers.ojornallusitano.utils.JournalData
import com.pexers.ojornallusitano.utils.JsonParser.getJournalsData
import com.pexers.ojornallusitano.utils.JsonParser.inputStreamToString
import com.pexers.ojornallusitano.utils.SharedPreferencesData


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var journals: List<JournalData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferencesData.init(applicationContext)  // Load shared preferences
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.toolbarMain.toolbarMain
        setSupportActionBar(toolbar)
        setupNav(toolbar)
        initRecyclerView(binding.recyclerViewJournals)
    }

    fun filterByCategory(cat: Categories) {
        val recyclerView = binding.recyclerViewJournals
        val adapter = recyclerView.adapter as CategoriesAdapter
        adapter.setData(if (cat == Categories.ALL) journals else journals.filter { j -> j.category.name == cat.name })
        recyclerView.startLayoutAnimation()
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        val journalsJson = inputStreamToString(application.assets.open("journals.json"))
        journals = getJournalsData(journalsJson).journals
        recyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
        }
        recyclerView.adapter = CategoriesAdapter(journals)
        recyclerView.startLayoutAnimation()
    }

    private fun setupNav(toolbar: Toolbar) {
        // Init nav toggle
        ActionBarDrawerToggle(
            this, binding.drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close
        ).syncState()
        // Setup on item selected listener
        val drawerLayout = binding.drawerLayout
        val categoriesFragment = CategoriesFragment()
        val favouritesFragment = FavouritesFragment()

        setCategoriesFragment(categoriesFragment) // Initial fragment
        binding.navigation.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_all -> {
                    setCategoriesFragment(categoriesFragment)
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    true
                }
                R.id.item_favourites -> {
                    setFavouritesFragment(favouritesFragment)
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

    private fun setCategoriesFragment(catFragment: CategoriesFragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout_main, catFragment)
            commit()
        }
    }

    private fun setFavouritesFragment(favFragment: FavouritesFragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout_main, favFragment)
            commit()
        }
    }
}