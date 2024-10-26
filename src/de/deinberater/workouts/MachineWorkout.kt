package de.deinberater.workouts

import de.deinberater.workouts.machines.Machine
import de.deinberater.workouts.workoutsets.DropSet
import de.deinberater.workouts.workoutsets.NormalSet
import de.deinberater.workouts.workoutsets.SpecialSet
import de.deinberater.workouts.workoutsets.WorkoutSet

data class MachineWorkout(val machine: Machine, val sets: MutableList<WorkoutSet> = mutableListOf()) {
    constructor(machineString: String, sets: MutableList<WorkoutSet> = mutableListOf()) : this(machineString.let {
        val regexMachine = Regex("^([^():]+)(?:\\((.+?)\\))?(?::.+)?$")
        val regexResult =
            regexMachine.matchEntire(it) ?: throw IllegalArgumentException("Machine info not found!")

        val machineName =
            regexResult.groups[1]!!.value.trim() // !! because first group is not optional and there is a match
        val machineDetails = regexResult.groups[2]?.value

        // Create the machine here, the name will be known and if there are details, they are added too
        return@let Machine(machineName, machineDetails)
    }, sets)

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
    fun getVolume(): Int {
        return sets.sumOf { it.getVolume(machine.getRealWeight(0.0)) }
    }


    /** Adds a set to the list of sets.
     * @param setInfo the information of the set as a string, at least including the weight
     * @throws IllegalArgumentException if the string cannot be parsed
     * @return itself
     * */
    fun addSetByString(setInfo: String, extraInfoOverride: String? = null): MachineWorkout {
        if (setInfo == "") throw IllegalArgumentException("Set info cannot be empty!")
        val regex = Regex("^(?:(?:(\\d+)x\\s)?(\\d+(?:.\\d+)?)(?:\\s\\((.+)\\))?|Dropsatz von (\\d+(?:.\\d+)?))\$")


        val match = regex.matchEntire(setInfo)
            ?: throw IllegalArgumentException("$setInfo does not match the correct pattern!")

        val dropSetWeight = match.groups[4]?.value
        val isDropSet = dropSetWeight != null

        val repsString = match.groups[1]?.value // Reps
        val weightString = (match.groups[2]?.value
            ?: dropSetWeight)!! // Weight: Either the second or the fourth group (dropSetWeight) are always present
        val extraInfo = match.groups[3]?.value ?: extraInfoOverride // extraInfo

        val reps: Int? = repsString?.toInt()
        val weight: Double = weightString.toDouble()

        val setToAdd: WorkoutSet = if (isDropSet) {
            DropSet(weight)
        } else if (extraInfo == null) {
            NormalSet(weight, reps)
        } else {
            SpecialSet(weight, extraInfo, reps)
        }

        sets.add(setToAdd)
        return this
    }

    override fun toString(): String {
        val machineString = machine.toString()
        val setsString = sets.joinToString()
        return "$machineString: $setsString"
    }
}
