/*
 * Copyright © 1/3/2023, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.utils

import java.net.HttpURLConnection
import java.net.URL

object Network {

    fun getJournalsJson(): String {
        // Synchronize Journals with SharedPreferences
        val url = URL("https://pexers.github.io/dummy/")
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        urlConnection.requestMethod = "GET"
        return try {
            if (urlConnection.responseCode == 200) {
                TextParser.parseInStreamToString(urlConnection.inputStream)
            } else ""
        } finally {
            urlConnection.disconnect()
        }
    }

}