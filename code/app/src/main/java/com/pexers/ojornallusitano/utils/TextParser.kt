/*
 * Copyright © 11/22/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.utils

import com.google.gson.Gson
import java.io.InputStream

object TextParser {

    private val gson = Gson()

    fun parseJsonToJournalsData(json: String): JournalsData =
        gson.fromJson(json, JournalsData::class.java)

    fun parseInStreamToString(inputStream: InputStream): String =
        inputStream.bufferedReader().use { it.readText() }

}
