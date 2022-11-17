/*
 * Copyright © 11/17/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesData {

    private lateinit var editor: SharedPreferences.Editor
    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("dataFile", Context.MODE_PRIVATE)
            editor = sharedPreferences!!.edit()
            editor.apply()
        }
    }

    fun saveSingleFavouriteData(journalName: String, toRemove: Boolean) {
        val copyOfSet = loadFavouritesData()
        if (toRemove) {
            copyOfSet.remove(journalName)
        } else
            copyOfSet.add(journalName)
        editor.putStringSet("favouritesSet", copyOfSet).apply()
    }

    fun loadFavouritesData(): MutableSet<String> {
        val savedSet = sharedPreferences!!.getStringSet("favouritesSet", sortedSetOf())
        return savedSet!!.toMutableSet()
    }

    fun deleteFavouritesData() {
        editor.remove("favouritesSet")
        editor.putStringSet("favouritesSet", emptySet()).apply()
    }

    fun setToDisplayFavourites(state: Boolean) {
        editor.putBoolean("toDisplayFavourites", state).apply()
    }

    /*
        fun getSavedCategory(): Categories {
            return if (toDisplayFavourites())
                Categories.FAVOURITES
            else Categories.ALL
        }
    */
    private fun toDisplayFavourites() = sharedPreferences!!.getBoolean("toDisplayFavourites", false)

}
