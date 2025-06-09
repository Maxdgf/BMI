package com.example.bmi.Modules

import kotlin.math.pow

class BMI {

    fun calculateBMI(mass: Double, height: Double): Double {
        val dataFormatter = DataFormatter()

        if (mass <= 0.0 && height <= 0.0) {
            return 0.0
        } else {
            val heightInMeters = height / 100
            return dataFormatter.formatDouble(mass / heightInMeters.pow(2), 1)
        }
    }

    fun checkBMI(bmi: Double): String {
        return if (bmi < 18.5) {
            "⚠\uFE0FBody weight deficiency. Possible dangers: \n-weakened immunity \n-osteoporosis \n-hormonal disorders \n-exhaustion \nIt is necessary to establish a proper diet, in more severe cases - to consult a nutritionist."
        } else if (bmi >= 18.5 && bmi < 25) {
            "✅Normal. It is necessary to continue to eat properly and move moderately."
        } else if (bmi >= 25 && bmi < 30) {
            "⚠\uFE0FOverweight (risk of obesity❗). The goal is proper nutrition and increased physical activity."
        } else if (bmi > 30) {
            "⚠\uFE0FObesity. Possible dangers: \n-diabetes mellitus \n-hypertension \n-stomach \n-intestinal diseases \n-musculoskeletal disorders \n-development of oncological diseases. \nContact a specialist immediately❗"
        } else {
            ""
        }
    }
}