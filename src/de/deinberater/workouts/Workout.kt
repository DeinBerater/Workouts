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

    /** Get the amount of exercises in this workout
     * */
    fun getAmountExercises() = exercises.size

    /** Get the total amount of sets in this workout
     * */
    fun getAmountSets() = exercises.sumOf { it.getAmountSets() }

    /** Returns the total weight lifted in this workout
     * */
    fun getTotalVolume() = exercises.sumOf { it.getVolume() }
}
