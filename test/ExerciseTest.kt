import de.deinberater.workouts.Exercise
import de.deinberater.workouts.machines.Machine
import de.deinberater.workouts.workoutcreator.DeinBeratersWorkoutCreator
import de.deinberater.workouts.workoutsets.DropSet
import de.deinberater.workouts.workoutsets.NormalSet
import de.deinberater.workouts.workoutsets.SpecialSet
import de.deinberater.workouts.workoutsets.WorkoutSet
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

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
    fun getAmountSets() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sets: MutableList<WorkoutSet> = mutableListOf(NormalSet(10.0), NormalSet(20.0))
        val sut = Exercise(machine, sets)
        val want = 2

        val have = sut.getAmountSets()

        assertEquals(want, have)
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

    @Test
    fun getStringEmpty() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = Exercise(
            machine
        )
        val want = "MTS Crunch (Höhe 8)"

        val have = sut.toString()

        assertEquals(want, have)
    }

    @Test
    fun getStringEmpty2() {
        val machine = Machine("MTS Crunch", "8")
        val sut = Exercise(
            machine
        )
        val want = "MTS Crunch (8)"

        val have = sut.toString()

        assertEquals(want, have)
    }

    @Test
    fun getStringEmpty3() {
        val machine = Machine("MTS Crunch")
        val sut = Exercise(
            machine
        )
        val want = "MTS Crunch"

        val have = sut.toString()

        assertEquals(want, have)
    }

    @Test
    fun getVolume() {
        val workoutCreator = DeinBeratersWorkoutCreator()

        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = Exercise(machine)
        sut.sets.addAll(
            listOf(
                workoutCreator.createWorkoutSet("10 (strange)"),
                workoutCreator.createWorkoutSet("10x 20"),
                workoutCreator.createWorkoutSet("27.5 (eh)")
            )
        )

        val want = 650.0

        val have = sut.getVolume()

        assertEquals(want, have)
    }


    @Test
    fun getVolume2() {
        val workoutCreator = DeinBeratersWorkoutCreator()

        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = Exercise(machine)
        sut.sets.addAll(
            listOf(
                workoutCreator.createWorkoutSet("10 (strange)"),
                workoutCreator.createWorkoutSet("10x 20"),
                workoutCreator.createWorkoutSet("Dropsatz von 10")
            )
        )

        val want = 400.0

        val have = sut.getVolume()

        assertEquals(want, have)
    }

    @Test
    fun getVolume3() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut =
            Exercise(
                machine, mutableListOf(
                    SpecialSet(10.0, "strange"),
                    NormalSet(20.0, 10),
                    SpecialSet(27.5, "eh", 8),
                    DropSet(30.0)
                )
            )
        val want = 780.0

        val have = sut.getVolume()

        assertEquals(want, have)
    }

    @Test
    fun getVolume4() {
        val machine = Machine("Deadlifts", "Höhe 8")
        val sut =
            Exercise(
                machine, mutableListOf(NormalSet(10.0), NormalSet(25.0))
            )
        val want = 900.0

        val have = sut.getVolume()

        assertEquals(want, have)
    }

    @Test
    fun getVolume5() {
        val machine = Machine("Trizeps Seilzug")
        val sut =
            Exercise(
                machine, mutableListOf(NormalSet(10.0), NormalSet(25.0))
            )
        val want = 420.0

        val have = sut.getVolume()

        assertEquals(want, have)
    }
}