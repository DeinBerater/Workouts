package de.deinberater.workouts

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class Workout(
    val type: WorkoutType,
    val date: LocalDate,
    val machines: MutableList<MachineWorkout> = mutableListOf(),
    var specialInfo: String? = null,
) {
    constructor(type: WorkoutType, string: String) :
            this(
                type,
                string.trim().split("\n").first().let { firstLine ->
                    val regex = Regex("^(?:.+?:\\s*)??([^\\s:]+)(?:(?::\\s*(.*))?|\\s+\\((.+?)\\))?\$")
                    val result = regex.matchEntire(firstLine)
                        ?: throw IllegalArgumentException("The first line of the workout details is incorrect!")
                    val dateToParse = result.groups[1]!!.value

                    val dateParsed = try {
                        LocalDate.parse(dateToParse, DateTimeFormatter.ofPattern("dd.MM.yy"))
                    } catch (exception: DateTimeParseException) {
                        throw IllegalArgumentException("Date could not be parsed!", exception)
                    }

                    return@let dateParsed
                }
            ) {
        val lines = string.trim().split("\n")

        val firstLine = lines.first()
        val match = Regex("^(?:.+?:\\s*)??([^\\s:]+)(?:(?::\\s*(.*))?|\\s+\\((.+?)\\))?\$").matchEntire(firstLine)
        val matchGroups = match!!.groups // Same regex as above, would have thrown an exception before already
        specialInfo = matchGroups[2]?.value ?: matchGroups[3]?.value // may be null

        if (lines.size == 1) return // If there is just one line, no machines are added
        lines.drop(1).forEach {
            addMachineWorkout(it.trim()) // If ANY line is bad, the whole constructor throws an exception
        }
    }

    init {
        if (date.isAfter(LocalDate.now())) throw IllegalArgumentException("Invalid date, workouts in the future are not allowed.")
    }

    fun addMachineWorkout(machineWorkoutInfo: String): Workout {
        if (machineWorkoutInfo == "") throw IllegalArgumentException()

        val regex =
            Regex("^[^():]+(?:\\(.+?\\))?(?::(.+))?$") // Unfortunately not much different than the regex in the MachineWorkout ctor
        val regexResult =
            regex.matchEntire(machineWorkoutInfo) ?: throw IllegalArgumentException("Machine info not found!")
        val machineWorkout = MachineWorkout(machineWorkoutInfo)

        val setsString = regexResult.groups[1]?.value?.trim()
        if (setsString != null) { // setString is null if there is just the machine name, e.g. "Blaues Teil"
            val regexSets =
                Regex("(?:(?<=^|,)|(?<=\\d\\s))\\s*?(?:.*?\\d+?(?=\\s[^(])|\\d(?=\\s[^(])|.+?(?=,|\$))")

            val setMatches = regexSets.findAll(setsString)

            var currentExtraDetails: String? = null // Stays until the end

            setMatches.forEach {
                var setInfoString = it.value.trim()

                val extraDetailsMatch = Regex("^(\\D.+?):(.+)").matchEntire(setInfoString)
                if (extraDetailsMatch != null) {
                    // If the regex matches, bot groups are there, because they are not optional. -> !!
                    currentExtraDetails = extraDetailsMatch.groups[1]!!.value // Set current details
                    setInfoString = extraDetailsMatch.groups[2]!!.value.trim() // Override info
                }

                machineWorkout.addSetByString(setInfoString, currentExtraDetails)
            }
        }

        machines.add(machineWorkout)
        return this
    }

    override fun toString(): String {
        val firstLine =
            date.format(DateTimeFormatter.ofPattern("dd.MM.yy")) + (if (specialInfo == null) "" else ": $specialInfo")
        val otherLines = machines.joinToString("\n")
        return (firstLine + "\n" + otherLines).trim()
    }
}
