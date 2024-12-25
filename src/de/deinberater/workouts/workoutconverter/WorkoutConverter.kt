package de.deinberater.workouts.workoutconverter

import de.deinberater.workouts.Exercise
import de.deinberater.workouts.Workout
import de.deinberater.workouts.machines.Machine
import de.deinberater.workouts.workoutcreator.WorkoutCreator
import de.deinberater.workouts.workoutsets.WorkoutSet

/** The contrary to a [WorkoutCreator]. A class which takes a workout and converts it to specific file formats.
 * @param T the output data type. Can be a string, but also a JSON object or anything similar.
 * */
interface WorkoutConverter<T> {
    /** Converts an entire workout
     * */
    fun convertWorkout(workout: Workout): T

    /** Converts an exercise
     * */
    fun convertExercise(exercise: Exercise): T

    /** Converts a machine
     * */
    fun convertMachine(machine: Machine): T

    /** Converts a workout set
     * */
    fun convertWorkoutSet(workoutSet: WorkoutSet): T
}