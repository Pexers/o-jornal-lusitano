/*
 * Copyright © 11/22/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.pexers.ojornallusitano.model.JournalsData
import java.io.InputStream

object TextParser {

    private val gson = Gson()

    fun parseJsonToJournalsData(json: String): JournalsData =
        gson.fromJson(json, JournalsData::class.java)

    fun parseInStreamToString(inputStream: InputStream): String =
        inputStream.bufferedReader().use { it.readText() }

    fun parseJournalsFromAssets(context: Context) =
        parseInStreamToString(context.assets.open("journals.json"))

    fun isJsonValid(json: String?): Boolean {
        try {
            JsonParser.parseString(json)
        } catch (e: JsonSyntaxException) {
            return false
        }
        return true
    }
}
