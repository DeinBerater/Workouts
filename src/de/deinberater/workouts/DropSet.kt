package de.deinberater.workouts

data class DropSet(override val baseWeight: Double) : WorkoutSet(baseWeight) {
    override fun toString() = "Dropsatz von ${super.toString()}"

    override fun copyWithNewWeight(newWeight: Double): WorkoutSet {
        return copy(baseWeight = newWeight)
    }

    override fun getVolume(): Int {
        return (8 * baseWeight).toInt() // The 8 is to receive an estimate.
    }
}
