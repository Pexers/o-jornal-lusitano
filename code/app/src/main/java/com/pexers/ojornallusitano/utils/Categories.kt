/*
 * Copyright © 11/29/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.utils

import androidx.annotation.Keep
import com.pexers.ojornallusitano.R

@Keep
enum class Categories(val displayName: Int) {
    // @formatter:off
    ALL(R.string.category_all),
    ECONOMY_POLITICS(R.string.category_economy_politics),
    FASHION(R.string.category_fashion),
    GENERAL(R.string.category_general),
    SPORTS(R.string.category_sports),
    TECHNOLOGY(R.string.category_technology)
    // @formatter:on
}
