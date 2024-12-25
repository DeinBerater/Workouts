package de.deinberater.workouts.workoutsets

import de.deinberater.workouts.workoutconverter.DeinBeratersWorkoutConverter

abstract class WorkoutSet(open val baseWeight: Double) {
    override fun toString(): String {
        return DeinBeratersWorkoutConverter().convertWorkoutSet(this)
    }

    /**
     * @param extraWeight the extra weight to the [baseWeight], for example from barbells
    * */
    abstract fun getVolume(extraWeight: Double): Int

    abstract fun copyWithNewWeight(newWeight: Double): WorkoutSet
}