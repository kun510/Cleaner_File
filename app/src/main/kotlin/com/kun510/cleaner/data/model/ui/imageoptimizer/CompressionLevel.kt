package com.kun510.cleaner.data.model.ui.imageoptimizer

import com.kun510.cleaner.R

enum class CompressionLevel(val stringRes: Int, val defaultPercentage: Int) {
    LOW(R.string.low, defaultPercentage = 25), MEDIUM(
        R.string.medium,
        defaultPercentage = 50
    ),
    HIGH(R.string.high, defaultPercentage = 75)
}