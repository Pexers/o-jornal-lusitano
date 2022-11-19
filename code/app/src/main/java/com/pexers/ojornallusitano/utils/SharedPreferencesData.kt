/*
 * Copyright © 11/18/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesData {

    private lateinit var editor: SharedPreferences.Editor
    private var sharedPreferences: SharedPreferences? = null
    var favourites: MutableSet<String>? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("dataFile", Context.MODE_PRIVATE)
            favourites =
                sharedPreferences!!.getStringSet("favouritesSet", emptySet())!!.toMutableSet()
            editor = sharedPreferences!!.edit()
            editor.apply()
        }
    }

    fun addFavourite(journalName: String) {
        favourites!!.add(journalName)
        editor.putStringSet("favouritesSet", favourites).apply()
    }

    fun removeFavourite(journalName: String) {
        favourites!!.remove(journalName)
        editor.putStringSet("favouritesSet", favourites).apply()
        println(favourites)
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
