/*
 * Copyright © 11/22/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesData {

    private lateinit var editor: SharedPreferences.Editor
    private var sharedPreferences: SharedPreferences? = null
    var favourites: MutableSet<String>? = null

    private const val dataFile = "dataFile"
    private const val favouritesPref = "favouritesSet"
    private const val startOnFavouritesPref = "startOnFavourites"

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(dataFile, Context.MODE_PRIVATE)
            favourites =
                sharedPreferences!!.getStringSet("favouritesSet", emptySet())!!.toMutableSet()
            editor = sharedPreferences!!.edit()
            editor.apply()
        }
    }

    fun addFavourite(journalName: String) {
        favourites!!.add(journalName)
        editor.putStringSet(favouritesPref, favourites).apply()
    }

    fun removeFavourite(journalName: String) {
        favourites!!.remove(journalName)
        editor.putStringSet(favouritesPref, favourites).apply()
    }

    fun setStartOnFavourites(flag: Boolean) {
        editor.putBoolean(startOnFavouritesPref, flag).apply()
    }

    fun startsOnFavourites() = sharedPreferences!!.getBoolean(startOnFavouritesPref, false)

}
