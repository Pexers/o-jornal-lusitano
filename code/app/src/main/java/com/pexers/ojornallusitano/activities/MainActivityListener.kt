/*
 * Copyright © 11/23/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.activities

import android.content.Intent
import com.pexers.ojornallusitano.model.JournalData

interface MainActivityListener {

    fun startNewActivity(intent: Intent)

    fun startWebViewActivity(journal: JournalData)

}