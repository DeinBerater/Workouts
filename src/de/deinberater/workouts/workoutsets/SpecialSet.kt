package de.deinberater.workouts.workoutsets

data class SpecialSet(override val baseWeight: Double, val specialInfo: String, private val reps: Int? = null) :
    RepSet(baseWeight, reps) {
    override fun copyWithNewWeight(newWeight: Double): WorkoutSet {
        return copy(baseWeight = newWeight)
    }

    override fun equals(other: Any?): Boolean {
        return other is SpecialSet && super.equals(other)
    }

    override fun toString() = "${super.toString()} ($specialInfo)"
    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + specialInfo.hashCode()
        return result
    }
}