/*
 * Copyright © 11/22/2022, Pexers (https://github.com/Pexers)
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
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
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
import com.pexers.ojornallusitano.utils.MainActivityListener
import com.pexers.ojornallusitano.utils.SharedPreferencesData

class MainActivity : AppCompatActivity(), MainActivityListener {

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
        // Load shared preferences
        SharedPreferencesData.init(applicationContext)
        val startOnFavourites = SharedPreferencesData.startsOnFavourites()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.toolbarMain.toolbarNav
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)  // Remove title from ActionBar
        setupNav(toolbar, startOnFavourites)
        initRecyclerView(binding.recyclerViewMain, startOnFavourites)
        initAdView()
    }

    override fun startNewActivity(intent: Intent) {
        startActivity(intent)
    }

    override fun startWebViewActivity(journal: JournalData) {
        addToRecentQueue(journal)
        val webViewActivity = Intent(this, WebViewActivity::class.java)
        // Send URL as an intent parameter
        webViewActivity.putExtra("url", journal.url)
        startActivity(webViewActivity)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun initAdView() {
        MobileAds.initialize(this) {}
        binding.adViewMain.loadAd(AdRequest.Builder().build())
    }

    private fun initRecyclerView(recyclerView: RecyclerView, startOnFavourites: Boolean) {
        val journalsJson = inputStreamToString(application.assets.open("journals.json"))
        journals = getJournalsData(journalsJson).journals
        recyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
        }
        if (startOnFavourites) switchFragment(favouritesFrag, favouritesAdapt, filterByFavourites())
        else switchFragment(categoriesFrag, categoriesAdapt, journals)
    }

    private fun switchFragment(
        fragment: Fragment, adapter: Adapter<*>, journals: ArrayList<JournalData>
    ) {
        // Update fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout_main, fragment)
            commit()
        }
        binding.recyclerViewMain.adapter = adapter
        updateRecyclerView(journals)
    }

    private fun startAboutActivity() {
        val aboutActivity = Intent(this, AboutActivity::class.java)
        startActivity(aboutActivity)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    fun updateRecyclerView(dataSet: ArrayList<JournalData>, skipAnimation: Boolean = false) {
        val recyclerView = binding.recyclerViewMain
        (recyclerView.adapter as JournalsAdapter).setData(dataSet)
        if (!skipAnimation) recyclerView.startLayoutAnimation()
        checkEmptyRecyclerView()
    }

    fun setFavouritesEditState(isEditing: Boolean) {
        favouritesAdapt.isEditing = isEditing
        // Force onCreateViewHolder to be invoked
        binding.recyclerViewMain.adapter = favouritesAdapt
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

    private fun setupNav(toolbar: Toolbar, startOnFavourites: Boolean) {
        // Init nav toggle
        ActionBarDrawerToggle(
            this, binding.drawerLayoutMain, toolbar, R.string.drawer_open, R.string.drawer_close
        ).syncState()
        // Setup on item selected listener
        val drawerLayout = binding.drawerLayoutMain
        var currentItemId: Int = if (startOnFavourites) R.id.item_favourites else R.id.item_all
        binding.navigationView.setNavigationItemSelectedListener {
            if (it.itemId == currentItemId && it.itemId != R.id.item_about) {
                closeDrawer(drawerLayout)
                false
            } else {
                currentItemId = it.itemId
                when (it.itemId) {
                    R.id.item_all -> {
                        switchFragment(categoriesFrag, categoriesAdapt, journals)
                        SharedPreferencesData.setStartOnFavourites(false)
                        closeDrawer(drawerLayout)
                        true
                    }
                    R.id.item_favourites -> {
                        switchFragment(favouritesFrag, favouritesAdapt, filterByFavourites())
                        SharedPreferencesData.setStartOnFavourites(true)
                        closeDrawer(drawerLayout)
                        true
                    }
                    R.id.item_recent -> {
                        switchFragment(recentFrag, categoriesAdapt, recentJournals)
                        closeDrawer(drawerLayout)
                        true
                    }
                    R.id.item_about -> {
                        startAboutActivity()
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

    fun checkEmptyRecyclerView() {
        val dataSet = (binding.recyclerViewMain.adapter as JournalsAdapter).getData()
        if (dataSet.isEmpty()) {
            binding.textViewEmptyRecyclerView.visibility = View.VISIBLE
        } else binding.textViewEmptyRecyclerView.visibility = View.GONE
    }

}
