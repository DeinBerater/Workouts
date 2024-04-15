package de.deinberater.workouts

class Machine(val name: String, val details: String? = null) {
    val type: MachineType
    private val baseWeight: Double

    init {
        val moreInfo = Machines.getMoreInfoByName(name)
        type = moreInfo["type"] as MachineType
        baseWeight = moreInfo["baseWeight"] as Double
    }

    /** Checks if the machine is the same */
    fun same(otherMachine: Machine): Boolean {
        return name.lowercase() == otherMachine.name.lowercase()
    }

    /** Checks if the machine is the same, including the details
     * Slightly different to equals(), because both details are formatted before being compared. */
    fun isEqual(other: Machine?): Boolean {
        if (other !is Machine) return false
        val detailsSame = formatDetails(details).equals(formatDetails(other.details))
        return same(other) && detailsSame
    }

    override fun equals(other: Any?): Boolean {
        return other is Machine && toString() == other.toString() && hashCode() == other.hashCode()
    }

    private fun formatDetails(details: String?): String? {
        return details?.lowercase()?.replace(":", "")
    }

    fun getRealWeight(weight: Double): Double {
        return baseWeight + weight
    }

    override fun toString(): String {
        return name + (if (details != null) " ($details)" else "")
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (details?.hashCode() ?: 0)
        return result
    }
}