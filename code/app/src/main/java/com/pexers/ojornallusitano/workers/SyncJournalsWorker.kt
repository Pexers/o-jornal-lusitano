/*
 * Copyright © 1/3/2023, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.pexers.ojornallusitano.utils.Network
import com.pexers.ojornallusitano.utils.SharedPreferencesData

/** Syncs SharedPreferences Journals data with GitHub Pages API **/
class SyncJournalsWorker(context: Context, parameters: WorkerParameters) :
    Worker(context, parameters) {

    override fun doWork(): Result {
        val responseJson = Network.getJournalsJson()  // Request journals from GitHub Pages API
        return if (responseJson != null) {  // Null indicates the response is not valid
            SharedPreferencesData.setJournals(responseJson)  // Save Journals JSON
            Result.success()
        } else Result.failure()  // Service temporarily unavailable
    }

}
