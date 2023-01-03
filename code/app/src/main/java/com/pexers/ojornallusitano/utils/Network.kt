/*
 * Copyright © 1/3/2023, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.utils

import java.net.HttpURLConnection
import java.net.URL

object Network {

    fun getJournalsJson(): String? {
        val url = URL("https://pexers.github.io/o-jornal-lusitano/")
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        urlConnection.requestMethod = "GET"
        urlConnection.setRequestProperty("Accept", "application/json;charset=utf-8")
        try {
            if (urlConnection.responseCode == 200) {
                val responseJson = TextParser.parseInStreamToString(urlConnection.inputStream)
                // Validate JSON response
                if (responseJson.isNotBlank() && TextParser.isJsonValid(responseJson)) {
                    return responseJson
                }
            }
            return null
        } finally {
            urlConnection.disconnect()
        }
    }

}
