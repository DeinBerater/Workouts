package de.deinberater.workouts

import de.deinberater.workouts.workoutconverter.DeinBeratersWorkoutConverter
import java.time.LocalDate

data class Workout(
    val type: WorkoutType,
    val date: LocalDate,
    val exercises: MutableList<Exercise> = mutableListOf(),
    var specialInfo: String? = null,
) {
    init {
        if (date.isAfter(LocalDate.now())) throw IllegalArgumentException("Invalid date, workouts in the future are not allowed.")
    }

    override fun toString(): String {
        return DeinBeratersWorkoutConverter().convertWorkout(this)
    }
}
