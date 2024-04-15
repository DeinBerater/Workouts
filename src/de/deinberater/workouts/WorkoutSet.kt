package de.deinberater.workouts

import kotlin.math.floor

abstract class WorkoutSet(open val baseWeight: Double) {

    override fun toString(): String {
        val isWholeNumber = floor(baseWeight) == baseWeight
        if (isWholeNumber) return baseWeight.toInt().toString()
        return baseWeight.toString()
    }

    abstract fun getVolume(): Int

    abstract fun copyWithNewWeight(newWeight: Double): WorkoutSet
}