/*
 * Copyright © 11/29/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesData {

    private lateinit var editor: SharedPreferences.Editor
    private var sharedPreferences: SharedPreferences? = null
    var favourites: MutableSet<String>? = null

    private const val dataFile = "dataFile"
    private const val journalsPref = "journalsJson"
    private const val favouritesPref = "favouritesSet"
    private const val startOnFavouritesPref = "startOnFavourites"

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(dataFile, Context.MODE_PRIVATE)
            sharedPreferences!!.apply {
                favourites = getStringSet(favouritesPref, emptySet())!!.toMutableSet()
                editor = edit()
            }
        }
    }

    fun getJournals() = sharedPreferences!!.getString(journalsPref, "{}")!!

    fun setJournals(journalsJson: String) =
        editor.putString(journalsPref.ifBlank { "{}" }, journalsJson).apply()

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
