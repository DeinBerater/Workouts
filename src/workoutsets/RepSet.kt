package workoutsets

abstract class RepSet(override val baseWeight: Double, private val reps: Int?) : WorkoutSet(baseWeight) {
    override fun toString(): String {
        return (if (reps() != 12) "${reps}x " else "") + super.toString()
    }

    override fun equals(other: Any?): Boolean {
        return other is RepSet && baseWeight == other.baseWeight && reps() == other.reps() && toString() == other.toString() && hashCode() == other.hashCode()
    }

    fun reps(): Int {
        return reps ?: 12
    }

    override fun hashCode(): Int {
        var result = baseWeight.hashCode()
        result = 31 * result + reps()
        return result
    }
}