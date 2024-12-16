import machines.Machine
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import workoutsets.DropSet
import workoutsets.NormalSet
import workoutsets.SpecialSet
import workoutsets.WorkoutSet

class ExerciseTest {

    @Test
    fun ctor() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = Exercise(machine)

        val have = sut.machine

        assertEquals(machine, have)
    }

    @Test
    fun ctorWithSets() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sets: MutableList<WorkoutSet> = mutableListOf(NormalSet(10.0), NormalSet(20.0))
        val sut = Exercise(machine, sets)

        val have = sut.sets

        assertEquals(sets, have)
    }

    @Test
    fun getTopSet() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sets: MutableList<WorkoutSet> = mutableListOf(NormalSet(10.0), NormalSet(20.0))
        val sut = Exercise(machine, sets)
        val want = NormalSet(20.0)

        val have = sut.getTopSet()

        assertEquals(want, have)
    }

    @Test
    fun getTopSetNoSets() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = Exercise(machine, mutableListOf())

        val have = sut.getTopSet()

        assertNull(have)
    }

    @Test
    fun getWeightDifferences() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = Exercise(machine, mutableListOf(NormalSet(10.0), NormalSet(20.0)))
        val want = listOf(10.0)

        val have = sut.getWeightDifferences()

        assertEquals(want, have)
    }

    @Test
    fun getWeightDifferences2() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = Exercise(machine, mutableListOf(NormalSet(10.0), NormalSet(20.0), NormalSet(15.0)))
        val want = listOf(10.0, -5.0)

        val have = sut.getWeightDifferences()

        assertEquals(want, have)
    }

    @Test
    fun getWeightDifferencesOtherSets() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = Exercise(machine, mutableListOf(NormalSet(10.0), SpecialSet(20.0, "weird"), DropSet(15.0)))
        val want = listOf(10.0, -5.0)

        val have = sut.getWeightDifferences()

        assertEquals(want, have)
    }

    @Test
    fun getWeightDifferencesEmpty() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = Exercise(machine)
        val want: List<Double> = listOf()

        val have = sut.getWeightDifferences()

        assertEquals(want, have)
    }

    @Test
    fun getWeightDifferencesOneElement() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = Exercise(machine, mutableListOf(DropSet(10.0)))
        val want: List<Double> = listOf()

        val have = sut.getWeightDifferences()

        assertEquals(want, have)
    }

    @Test
    fun getWeightDifferencesPercent() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = Exercise(machine, mutableListOf(NormalSet(10.0), NormalSet(20.0), NormalSet(15.0)))
        val want = listOf(100.0, -25.0)

        val have = sut.getRealWeightDifferencesPercent()

        assertEquals(want, have)
    }

    @Test
    fun getWeightDifferencesPercentEmpty() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = Exercise(machine)
        val want: List<Double> = listOf()

        val have = sut.getRealWeightDifferencesPercent()

        assertEquals(want, have)
    }

    @Test
    fun getWeightDifferencesPercentOneElement() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = Exercise(machine, mutableListOf(DropSet(10.0)))
        val want: List<Double> = listOf()

        val have = sut.getRealWeightDifferencesPercent()

        assertEquals(want, have)
    }

    @Test
    fun getWeightDifferencesWithBase() {
        val machine = Machine("Deadlifts")
        val sut = Exercise(machine, mutableListOf(NormalSet(0.0), NormalSet(20.0)))
        val want = listOf(100.0) // Weight from 20 kg (Base) to 40 kg

        val have = sut.getRealWeightDifferencesPercent()

        assertEquals(want, have)
    }


    @Test
    fun getSetsRealWeight() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sets: MutableList<WorkoutSet> = mutableListOf(NormalSet(10.0), NormalSet(25.0))
        val sut = Exercise(machine, sets)

        val have = sut.getSetsRealWeight()

        assertEquals(sets, have)
    }

    @Test
    fun getSetsRealWeight2() {
        val machine = Machine("Deadlifts", "Höhe 8")
        val sets: MutableList<WorkoutSet> = mutableListOf(NormalSet(10.0), NormalSet(25.0))
        val sut = Exercise(machine, sets)
        val want: MutableList<WorkoutSet> = mutableListOf(NormalSet(30.0), NormalSet(45.0))

        val have = sut.getSetsRealWeight()

        assertEquals(want, have)
    }

    @Test
    fun getSetsRealWeight3() {
        val machine = Machine("Deadlifts", "Höhe 8")
        val sets: MutableList<WorkoutSet> = mutableListOf(NormalSet(10.0), NormalSet(25.0))
        val sut = Exercise(machine, sets)

        val have = sut.getSetsRealWeight()

        // Sets should be untouched
        assertNotEquals(sets, have)
    }

    @Test
    fun getString() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = Exercise(
            machine, mutableListOf(
                SpecialSet(10.0, "strange"),
                NormalSet(20.0, 10),
                SpecialSet(27.5, "eh"),
                DropSet(30.0)
            )
        )
        val want = "MTS Crunch (Höhe 8): 10 (strange), 10x 20, 27.5 (eh), Dropsatz von 30"

        val have = sut.toString()

        assertEquals(want, have)
    }
}