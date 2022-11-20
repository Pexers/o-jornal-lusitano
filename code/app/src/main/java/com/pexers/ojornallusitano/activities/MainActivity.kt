/*
 * Copyright © 11/20/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pexers.ojornallusitano.R
import com.pexers.ojornallusitano.adapters.CategoriesAdapter
import com.pexers.ojornallusitano.adapters.FavouritesAdapter
import com.pexers.ojornallusitano.adapters.JournalsAdapter
import com.pexers.ojornallusitano.databinding.ActivityMainBinding
import com.pexers.ojornallusitano.fragments.Categories
import com.pexers.ojornallusitano.fragments.CategoriesFragment
import com.pexers.ojornallusitano.fragments.FavouritesFragment
import com.pexers.ojornallusitano.fragments.RecentFragment
import com.pexers.ojornallusitano.utils.JournalData
import com.pexers.ojornallusitano.utils.JsonParser.getJournalsData
import com.pexers.ojornallusitano.utils.JsonParser.inputStreamToString
import com.pexers.ojornallusitano.utils.SharedPreferencesData
import com.pexers.ojornallusitano.utils.WebViewListener

class MainActivity : AppCompatActivity(), WebViewListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var journals: ArrayList<JournalData>
    private val recentJournals = arrayListOf<JournalData>()

    private val categoriesAdapt = CategoriesAdapter(arrayListOf(), this)
    private val favouritesAdapt = FavouritesAdapter(arrayListOf(), this)

    private val categoriesFrag = CategoriesFragment()
    private val favouritesFrag = FavouritesFragment()
    private val recentFrag = RecentFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferencesData.init(applicationContext)  // Load shared preferences
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.toolbarMain.toolbarNav
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)  // Remove title from ActionBar
        setupNav(toolbar, binding.recyclerViewMain)
        initRecyclerView(binding.recyclerViewMain)
    }

    override fun switchToWebViewActivity(journal: JournalData) {
        addToRecentQueue(journal)
        val webViewActivity = Intent(this, WebViewActivity::class.java)
        // Send URL as an intent parameter
        webViewActivity.putExtra("url", journal.url)
        startActivity(webViewActivity)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    fun updateRecyclerView(dataSet: ArrayList<JournalData>, skipAnimation: Boolean = false) {
        val recyclerView = binding.recyclerViewMain
        (recyclerView.adapter as JournalsAdapter).setData(dataSet)
        if (!skipAnimation) recyclerView.startLayoutAnimation()
        checkEmptyRecyclerView(dataSet)
    }

    private fun updateFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout_main, fragment)
            commit()
        }
    }

    fun filterByCategory(category: Categories) =
        if (category == Categories.ALL) journals else journals.filter { j -> j.category.name == category.name } as ArrayList<JournalData>

    fun filterByInput(input: String, dataSet: ArrayList<JournalData>) =
        if (input.isBlank()) dataSet else dataSet.filter { j ->
            j.name.trim().contains(input.trim(), ignoreCase = true)
        } as ArrayList<JournalData>

    fun clearRecentQueue() {
        recentJournals.clear()
        updateRecyclerView(arrayListOf(), true)
    }

    private fun filterByFavourites() =
        journals.filter { j -> SharedPreferencesData.favourites!!.contains(j.name) } as ArrayList<JournalData>

    private fun initRecyclerView(recyclerView: RecyclerView) {
        val journalsJson = inputStreamToString(application.assets.open("journals.json"))
        journals = getJournalsData(journalsJson).journals
        recyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
        }
        // TODO: Set last adapter used by user
        updateFragment(categoriesFrag)
        recyclerView.adapter = categoriesAdapt
        updateRecyclerView(journals)
    }

    private fun setupNav(toolbar: Toolbar, recyclerView: RecyclerView) {
        // Init nav toggle
        ActionBarDrawerToggle(
            this, binding.drawerLayoutMain, toolbar, R.string.drawer_open, R.string.drawer_close
        ).syncState()
        // Setup on item selected listener
        val drawerLayout = binding.drawerLayoutMain
        var currentItemId: Int = R.id.item_all
        binding.navigationView.setNavigationItemSelectedListener {
            if (it.itemId == currentItemId) {
                closeDrawer(drawerLayout)
                false
            } else {
                currentItemId = it.itemId
                when (it.itemId) {
                    R.id.item_all -> {
                        updateFragment(categoriesFrag)
                        recyclerView.adapter = categoriesAdapt
                        updateRecyclerView(journals)
                        closeDrawer(drawerLayout)
                        true
                    }
                    R.id.item_favourites -> {
                        updateFragment(favouritesFrag)
                        recyclerView.adapter = favouritesAdapt
                        updateRecyclerView(filterByFavourites())
                        closeDrawer(drawerLayout)
                        true
                    }
                    R.id.item_recent -> {
                        updateFragment(recentFrag)
                        recyclerView.adapter = categoriesAdapt
                        updateRecyclerView(recentJournals)
                        closeDrawer(drawerLayout)
                        true
                    }
                    R.id.item_about -> {
                        closeDrawer(drawerLayout)
                        true
                    }
                    else -> {
                        closeDrawer(drawerLayout)
                        false
                    }
                }
            }
        }
    }

    private fun addToRecentQueue(journal: JournalData) {
        if (recentJournals.contains(journal)) recentJournals.remove(journal)
        else if (recentJournals.size == 10) recentJournals.removeLast()
        recentJournals.add(0, journal)
    }

    private fun closeDrawer(drawerLayout: DrawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    private fun checkEmptyRecyclerView(dataSet: ArrayList<JournalData>) {
        if (dataSet.isEmpty()) {
            binding.textViewEmptyRecyclerView.visibility = View.VISIBLE
        } else binding.textViewEmptyRecyclerView.visibility = View.GONE
    }

}
