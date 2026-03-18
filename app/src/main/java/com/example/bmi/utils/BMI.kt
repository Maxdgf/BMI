package com.example.bmi.utils

import androidx.compose.runtime.Immutable
import java.util.Locale
import kotlin.math.pow

@Immutable
data class BMIData(
    val index: String, // num string
    val description: String,
    val dangers: Set<String>?
)

class BMI {
    fun calculateBMI(mass: Double, height: Double): Pair<BMIData?, String?> =
        try {
            val heightInMeters = height / 100
            val bmi = mass / heightInMeters.pow(2)
            val formatted = String.format(Locale.US, "%.2f", bmi)
            val bmiData = checkBMI(bmi)

            Pair(
                BMIData(
                    index = formatted,
                    description = bmiData.first,
                    dangers = bmiData.second
                ),
                null
            )
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            Pair(null, e.message) // put exception message
        }

    private fun checkBMI(bmi: Double): Pair<String, Set<String>?> =
        when {
            bmi == 16.0 -> Pair("⚠\uFE0FCritically severe underweight, seek help immediately!", null)
            bmi in 16.0..18.5 -> Pair("⚠\uFE0FBody weight deficiency.", setOf<String>("weakened immunity", "osteoporosis", "exhaustion"))
            bmi in 18.5..25.0 -> Pair("✅Normal. It is necessary to continue to eat properly and move moderately.", null)
            bmi in 25.0..30.0 -> Pair("⚠\uFE0FOverweight (risk of obesity❗). The goal is proper nutrition and increased physical activity.", null)
            bmi > 30 -> Pair(
                "⚠\uFE0FObesity.",
                setOf<String>(
                    "diabetes mellitus",
                    "hypertension",
                    "stomach",
                    "intestinal diseases",
                    "musculoskeletal disorders",
                    "development of oncological diseases"
                )
            )
            else -> throw IllegalArgumentException("Such a BMI cannot exist -> $bmi") // throw exception
        }
}