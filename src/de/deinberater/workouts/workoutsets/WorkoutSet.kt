package de.deinberater.workouts.workoutsets

import kotlin.math.floor

abstract class WorkoutSet(open val baseWeight: Double) {

    override fun toString(): String {
        val isWholeNumber = floor(baseWeight) == baseWeight
        if (isWholeNumber) return baseWeight.toInt().toString()
        return baseWeight.toString()
    }


    /**
     * @param extraWeight the extra weight to the [baseWeight], for example from barbells
    * */
    abstract fun getVolume(extraWeight: Double): Int

    abstract fun copyWithNewWeight(newWeight: Double): WorkoutSet
}