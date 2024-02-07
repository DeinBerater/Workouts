package workoutsets

data class DropSet(override val baseWeight: Double) : WorkoutSet(baseWeight) {
    override fun toString() = "Dropsatz von ${super.toString()}"

    override fun copyWithNewWeight(newWeight: Double): WorkoutSet {
        return copy(baseWeight = newWeight)
    }
}
