/*
 * Copyright © 11/20/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.utils

import com.google.gson.Gson
import java.io.InputStream


object JsonParser {

    private val gson = Gson()

    fun getJournalsData(json: String): JournalsData = gson.fromJson(json, JournalsData::class.java)

    fun inputStreamToString(inputStream: InputStream): String =
        inputStream.bufferedReader().use { it.readText() }

}
