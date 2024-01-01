package com.mc.rapidrescue

import kotlin.math.abs

object UserHealthFuzzyInferenceSystem {

    private var anamolyCounter = 0
    private var maxAnamolies = 25

    private var averageHeartRate = 0
    private var averageRespRate = 0

    private const val deltaHeartRate = 10
    private const val deltaRespRate = 5

    fun fuzzifyUserHealthAnamoly(index: Int, respRateArray: Array<String>,
                                  heartRateArray: Array<String>): Boolean {
        averageHeartRate = UserMonitoringData.getUserRespRateArray().average().toInt()
        averageRespRate = UserMonitoringData.getUserHeartRateArray().average().toInt()

        if (abs(respRateArray[index].toInt() - averageRespRate) > deltaRespRate ||
            abs(heartRateArray[index].toInt() - averageHeartRate) > deltaHeartRate
        ) {
            anamolyCounter++
        }
        return defuzzifyUserHealthAnamoly()
    }

    fun defuzzifyUserHealthAnamoly(): Boolean{
        if (anamolyCounter > maxAnamolies) {
            anamolyCounter = 0
            return true
        }
        return false
    }
}