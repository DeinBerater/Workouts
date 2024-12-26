package de.deinberater.workouts.workoutcreator

import de.deinberater.workouts.Exercise
import de.deinberater.workouts.Workout
import de.deinberater.workouts.WorkoutType
import de.deinberater.workouts.machines.Machine
import de.deinberater.workouts.workoutsets.DropSet
import de.deinberater.workouts.workoutsets.NormalSet
import de.deinberater.workouts.workoutsets.SpecialSet
import de.deinberater.workouts.workoutsets.WorkoutSet
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/** My own workout creator, which matches my structure of writing down workouts
 * */
class DeinBeratersWorkoutCreator : WorkoutCreator<String> {


    override fun createWorkout(data: String): Workout {
        val lines = data.trim().split("\n")
        val firstLine = lines.first()

        val match = Regex("^(?:.+?:\\s*)??([^\\s:]+)(?:(?::\\s*(.*))?|\\s+\\((.+?)\\))?\$").matchEntire(firstLine)
            ?: throw IllegalArgumentException("The first line of the workout details ($firstLine) is incorrect!")

        val dateToParse = match.groups[1]!!.value

        val date = try {
            LocalDate.parse(dateToParse, DateTimeFormatter.ofPattern("dd.MM.yy"))
        } catch (exception: DateTimeParseException) {
            throw IllegalArgumentException("Date could not be parsed!", exception)
        }

        val matchGroups = match.groups
        val specialInfo = matchGroups[2]?.value ?: matchGroups[3]?.value // may be null

        val exercises = mutableListOf<Exercise>()

        // If there is just one line, no exercises are added
        if (lines.size > 1) {
            lines.drop(1).forEach {
                exercises.add(createExercise(it.trim())) // If ANY line is bad, the whole constructor throws an exception
            }
        }

        return Workout(WorkoutType.Unknown, date, exercises, specialInfo)
    }


    override fun createExercise(data: String): Exercise {
        if (data == "") throw IllegalArgumentException("Exercise info cannot be empty!")

        // Group 1: Sets (optional)
        val regex = Regex("(^[^():]+(?:\\(.+?\\))?)(?::(.+))?$")
        val regexResult = regex.matchEntire(data) ?: throw IllegalArgumentException("Workout machine info not found!")


        val machine = createMachine(regexResult.groups[1]!!.value) // !! because the group is not optional
        val exercise = Exercise(machine)

        // The machine and the workout are created here. Now sets are added

        val setsString = regexResult.groups[2]?.value?.trim()
        if (setsString != null) { // setString is null if there is just the machine name, e.g. "Blaues Teil"
            val regexSets =
                Regex("(?:(?<=^|,)|(?<=\\d\\s))\\s*?(?:\\d(?=\\s[^(])|[^,]+?\\d(?=\\s[^(])|.+?(?=,|\$))")

            val setMatches = regexSets.findAll(setsString)

            var currentExtraDetails: String? = null

            // Loop each set
            setMatches.forEach {
                var setInfoString = it.value.trim()

                val extraDetailsMatch = Regex("^(\\D.+?):(.+)").matchEntire(setInfoString)
                if (extraDetailsMatch != null) {
                    // If the regex matches, both groups are there, because they are not optional. -> !! is fine
                    currentExtraDetails = extraDetailsMatch.groups[1]!!.value // Set current details
                    setInfoString = extraDetailsMatch.groups[2]!!.value.trim() // Override info
                }

                // Add the set to the exercise
                exercise.sets.add(createWorkoutSet(setInfoString, currentExtraDetails))
            }
        }

        return exercise
    }

    override fun createMachine(data: String): Machine {
        // Group 1: Machine name
        // Group 2: Machine details (optional)
        val machineRegex = Regex("^([^():]+)(?:\\((.+?)\\))?")
        val regexResult = machineRegex.matchEntire(data) ?: throw IllegalArgumentException("Machine info invalid!")

        val machineName =
            regexResult.groups[1]!!.value.trim() // !! because first group is not optional and there is a match
        val machineDetails = regexResult.groups[2]?.value

        return Machine(machineName, machineDetails)
    }

    /** If there is extra info, create a workout set with it
     * */
    private fun createWorkoutSet(data: String, extraInfo: String?): WorkoutSet {
        // If there is no extra info or there is extra info in brackets, create the set just using the data
        if (extraInfo == null || Regex("\\(.*\\)").containsMatchIn(data)) return createWorkoutSet(data)
        return createWorkoutSet("$data ($extraInfo)")
    }

    /** Creates a set.
     * @throws IllegalArgumentException if the string cannot be parsed
     * @return the created set
     * */
    override fun createWorkoutSet(data: String): WorkoutSet {
        if (data == "") throw IllegalArgumentException("Set info cannot be empty!")
        val regex = Regex("^(?:(?:(\\d+)x\\s)?(\\d+(?:.\\d+)?)(?:\\s\\((.+)\\))?|Dropsatz von (\\d+(?:.\\d+)?))\$")

        val match = regex.matchEntire(data)
            ?: throw IllegalArgumentException("$data does not match the correct pattern!")

        val dropSetWeight = match.groups[4]?.value
        val isDropSet = dropSetWeight != null

        val repsString = match.groups[1]?.value // Reps
        val weightString = (match.groups[2]?.value
            ?: dropSetWeight)!! // Weight: Either the second or the fourth group (dropSetWeight) are always present
        val extraInfo = match.groups[3]?.value // extraInfo

        val reps: Int? = repsString?.toInt()
        val weight: Double = weightString.toDouble()

        val setToAdd: WorkoutSet = if (isDropSet) {
            DropSet(weight)
        } else if (extraInfo == null) {
            NormalSet(weight, reps)
        } else {
            SpecialSet(weight, extraInfo, reps)
        }

        return setToAdd
    }
}