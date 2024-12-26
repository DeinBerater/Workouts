package de.deinberater.workouts.workoutsets

data class DropSet(override val baseWeight: Double) : WorkoutSet(baseWeight) {
    override fun toString() = super.toString()

    override fun copyWithNewWeight(newWeight: Double): WorkoutSet {
        return copy(baseWeight = newWeight)
    }

    override fun getVolume(extraWeight: Double): Double {
        return (8 * (baseWeight + extraWeight)) // The 8 is to receive an estimate.
    }
}
