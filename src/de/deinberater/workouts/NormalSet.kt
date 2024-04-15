package de.deinberater.workouts

data class NormalSet(override val baseWeight: Double, private val reps: Int? = null) : RepSet(baseWeight, reps) {

    override fun toString() = super.toString()


    override fun equals(other: Any?): Boolean {
        return other is NormalSet && super.equals(other)
    }

    override fun copyWithNewWeight(newWeight: Double): WorkoutSet {
        return copy(baseWeight = newWeight)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}