package com.example.bmi.Modules

import java.util.Locale

class DataFormatter {

    fun formatDouble(value: Double, numsAfterDot: Int): Double {
        val formattedValue = String.format(Locale.US, "%.${numsAfterDot}f", value)
        return formattedValue.toDouble()
    }
}