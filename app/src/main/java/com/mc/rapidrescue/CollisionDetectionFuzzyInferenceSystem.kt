package com.mc.rapidrescue

import kotlin.math.abs

object CollisionDetectionFuzzyInferenceSystem {

    private var prevSpeed = 0f
    private var suddenDeceleration = 0
    private var maxSuddenDeceleration = 5

    // As per average car speed in cities in metres per seconds
    private const val deltaSpeedChange = 19
    private const val speedCollisionFactor = 0.75

    fun fuzzifyCollisionParameters(currSpeed: Float): Boolean {
        if (abs(abs(currSpeed) - prevSpeed) > deltaSpeedChange * speedCollisionFactor) {
            suddenDeceleration++
        }
        prevSpeed = abs(currSpeed)
        return defuzzifyCollisionParameters()
    }

    fun defuzzifyCollisionParameters(): Boolean{
        if (suddenDeceleration > maxSuddenDeceleration) {
            suddenDeceleration = 0
            return true
        }
        return false
    }
}