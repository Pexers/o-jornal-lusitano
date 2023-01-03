/*
 * Copyright © 1/3/2023, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.model

import androidx.annotation.Keep

@Keep
data class JournalsData(
    val journals: ArrayList<JournalData>?
)

@Keep
data class JournalData(
    val name: String,
    val url: String,
    val category: Categories
)
