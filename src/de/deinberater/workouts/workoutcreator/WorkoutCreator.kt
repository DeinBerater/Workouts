package de.deinberater.workouts.workoutcreator

import de.deinberater.workouts.Exercise
import de.deinberater.workouts.Workout
import de.deinberater.workouts.machines.Machine
import de.deinberater.workouts.workoutsets.WorkoutSet

/** A class which can create a workout from specific file formats.
 * @param T the input data type. Can be a string, but also a JSON object or anything similar.
 * */
interface WorkoutCreator<T> {
    /** Create a whole workout object from the input data
     * */
    fun createWorkout(data: T): Workout

    /** Create an exercise from the input data
     * */
    fun createExercise(data: T): Exercise

    /** Creates a machine from the input data
     * */
    fun createMachine(data: T): Machine

    /** Create a workout set from the input data
     * */
    fun createWorkoutSet(data: T): WorkoutSet
}