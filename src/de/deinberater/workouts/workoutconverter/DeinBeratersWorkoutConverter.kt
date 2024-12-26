package de.deinberater.workouts.workoutconverter

import de.deinberater.workouts.Exercise
import de.deinberater.workouts.Workout
import de.deinberater.workouts.machines.Machine
import de.deinberater.workouts.workoutsets.DropSet
import de.deinberater.workouts.workoutsets.RepSet
import de.deinberater.workouts.workoutsets.SpecialSet
import de.deinberater.workouts.workoutsets.WorkoutSet
import java.time.format.DateTimeFormatter
import kotlin.math.floor

/** My own workout creator, which matches my structure of writing down workouts.
 * It's pretty simple but clearly structured and well readable.
 * */
class DeinBeratersWorkoutConverter : WorkoutConverter<String> {
    override fun convertWorkout(workout: Workout): String {
        val specialInfo = workout.specialInfo
        val firstLine =
            workout.date.format(DateTimeFormatter.ofPattern("dd.MM.yy")) + (if (specialInfo == null) "" else ": $specialInfo")
        val otherLines = workout.exercises.joinToString("\n") { convertExercise(it) }
        return (firstLine + "\n" + otherLines).trim()
    }

    override fun convertExercise(exercise: Exercise): String {
        var result = convertMachine(exercise.machine)

        if (exercise.getAmountSets() > 0) {
            val setsString = exercise.sets.joinToString { convertWorkoutSet(it) }
            result += ": $setsString"
        }

        return result
    }

    override fun convertMachine(machine: Machine): String {
        val machineDetails = machine.details
        return machine.name + (if (machineDetails != null) " ($machineDetails)" else "")
    }

    override fun convertWorkoutSet(workoutSet: WorkoutSet): String {
        val baseWeight = workoutSet.baseWeight
        val isWholeNumber = floor(baseWeight) == baseWeight

        var result: String = if (isWholeNumber) baseWeight.toInt().toString()
        else baseWeight.toString()

        // For every type of set, change the text a little
        if (workoutSet is SpecialSet) result += " (${workoutSet.specialInfo})"
        if (workoutSet is RepSet) result = (if (workoutSet.reps() != 12) "${workoutSet.reps()}x " else "") + result
        if (workoutSet is DropSet) result = "Dropsatz von $result"

        return result
    }
}