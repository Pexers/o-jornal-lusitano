/*
 * Copyright © 11/21/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.utils

import android.content.Intent

interface MainActivityListener {

    fun startNewActivity(intent: Intent)

    fun switchToWebViewActivity(journal: JournalData)

}