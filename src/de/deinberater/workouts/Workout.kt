package de.deinberater.workouts

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Workout(
    val type: WorkoutType,
    val date: LocalDate,
    val machines: MutableList<Exercise> = mutableListOf(),
    var specialInfo: String? = null,
) {
    init {
        if (date.isAfter(LocalDate.now())) throw IllegalArgumentException("Invalid date, workouts in the future are not allowed.")
    }

    override fun toString(): String {
        val firstLine =
            date.format(DateTimeFormatter.ofPattern("dd.MM.yy")) + (if (specialInfo == null) "" else ": $specialInfo")
        val otherLines = machines.joinToString("\n")
        return (firstLine + "\n" + otherLines).trim()
    }
}
