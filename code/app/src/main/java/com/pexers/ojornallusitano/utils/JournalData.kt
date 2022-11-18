/*
 * Copyright © 11/18/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.utils

import com.pexers.ojornallusitano.fragments.Categories

data class JournalsData(
    val journals: List<JournalData>
)

data class JournalData(
    val name: String,
    val url: String,
    val category: Categories
)
