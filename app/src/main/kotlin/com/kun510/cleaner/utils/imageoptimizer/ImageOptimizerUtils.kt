package com.kun510.cleaner.utils.imageoptimizer

import com.kun510.cleaner.data.model.ui.imageoptimizer.CompressionLevel

fun getCompressionLevelFromSliderValue(sliderValue: Float): CompressionLevel {
    return when {
        sliderValue < 33f -> CompressionLevel.LOW
        sliderValue < 66f -> CompressionLevel.MEDIUM
        else -> CompressionLevel.HIGH
    }
}