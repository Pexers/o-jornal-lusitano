/*
 * Copyright © 11/20/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.adapters

import com.pexers.ojornallusitano.model.JournalData

interface JournalsAdapter {

    fun getData(): ArrayList<JournalData>

    fun setData(data: ArrayList<JournalData>)

}
