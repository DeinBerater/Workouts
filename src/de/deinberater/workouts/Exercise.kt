package de.deinberater.workouts

import de.deinberater.workouts.machines.Machine
import de.deinberater.workouts.workoutconverter.DeinBeratersWorkoutConverter
import de.deinberater.workouts.workoutsets.WorkoutSet

data class Exercise(val machine: Machine, val sets: MutableList<WorkoutSet> = mutableListOf()) {
    fun getTopSet(): WorkoutSet? {
        // sets.sortedBy { it.baseWeight }.first()
        return sets.maxByOrNull { it.baseWeight }
    }

    fun getSetsRealWeight(): List<WorkoutSet> {
        return sets.map {
            it.copyWithNewWeight(machine.getRealWeight(it.baseWeight))
        }
    }

    /** Get a list of the differences in weights
     * @return A list the difference of weights in kg. This list has the length of the number of sets minus 1 or 0. */
    fun getWeightDifferences(): List<Double> {
        if (sets.size <= 1) return listOf()
        // sets.size > 1
        // b - a
        return sets.zipWithNext { a, b -> b.baseWeight - a.baseWeight }
    }

    /** Get a list of the weight differences in percent
     * @return A list the difference of weights in percent. This list has the length of the number of sets minus 1 or 0. */
    fun getRealWeightDifferencesPercent(): List<Double> {
        if (sets.size <= 1) return listOf()
        // sets.size > 1

        // (b / a - 1) * 100
        return getSetsRealWeight().zipWithNext { a, b -> (b.baseWeight / a.baseWeight - 1.0) * 100.0 }
    }

    /** Gets the total volume of this machine workout
     * @return The sum of the set volumes */
    fun getVolume(): Double {
        return sets.sumOf { it.getVolume(machine.getRealWeight(0.0)) }
    }

    /** Returns the amount of sets in this exercise
     * */
    fun getAmountSets() = sets.size

    override fun toString(): String {
        return DeinBeratersWorkoutConverter().convertExercise(this)
    }
}
