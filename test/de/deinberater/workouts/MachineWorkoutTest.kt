package de.deinberater.workouts

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MachineWorkoutTest {

    @Test
    fun ctor() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine)

        val have = sut.machine

        assertEquals(machine, have)
    }

    @Test
    fun ctorWithSets() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sets: MutableList<WorkoutSet> = mutableListOf(NormalSet(10.0), NormalSet(20.0))
        val sut = MachineWorkout(machine, sets)

        val have = sut.sets

        assertEquals(sets, have)
    }

    @Test
    fun ctor2() {
        val machine = Machine("MTS Crunch")
        val sut = MachineWorkout("MTS Crunch")
        val have = sut.machine

        assertEquals(machine, have)
    }

    @Test
    fun ctorWithSets2() {
        val sets: MutableList<WorkoutSet> = mutableListOf(NormalSet(10.0), NormalSet(20.0))
        val sut = MachineWorkout("MTS Crunch", sets)

        val have = sut.sets

        assertEquals(sets, have)
    }

    @Test
    fun ctor3() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout("MTS Crunch (Höhe 8)")
        val have = sut.machine

        assertEquals(machine, have)
    }

    @Test
    fun ctor4() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout("MTS Crunch (Höhe 9)")
        val have = sut.machine

        assertNotEquals(machine, have)
    }

    @Test
    fun ctor5() {
        val machine = Machine("MTS Crunch")
        val sut = MachineWorkout("MTS Crunch (Höhe 9)")
        val have = sut.machine

        assertNotEquals(machine, have)
    }

    @Test
    fun ctor6() {
        val machine = Machine("MTS Crunches")
        val sut = MachineWorkout("MTS Crunch")
        val have = sut.machine

        assertNotEquals(machine, have)
    }

    @Test
    fun ctor7() {
        val machine = Machine("MTS Crunch", "Höhe 9")
        val sut = MachineWorkout("MTS Crunch")
        val have = sut.machine

        assertNotEquals(machine, have)
    }

    @Test
    fun ctor8() {
        val machine = Machine("MTS Crunch", "Höhe: 9")
        val sut = MachineWorkout("MTS Crunch (Höhe: 9)")
        val have = sut.machine

        assertEquals(machine, have)
    }

    @Test
    fun ctorMachineInvalid() {
        assertThrows(IllegalArgumentException::class.java) {
            MachineWorkout(": MTS Crunch")
        }
    }

    @Test
    fun ctorMachineInvalid2() {
        assertThrows(IllegalArgumentException::class.java) {
            MachineWorkout("(MTS Crunch)")
        }
    }

    @Test
    fun ctorMachineInvalid3() {
        assertThrows(IllegalArgumentException::class.java) {
            MachineWorkout("MTS Crunch ()")
        }
    }

    @Test
    fun ctorMachineInvalid4() {
        assertThrows(IllegalArgumentException::class.java) {
            MachineWorkout("MTS Crunch (Höhe 9):")
        }
    }


    @Test
    fun getTopSet() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sets: MutableList<WorkoutSet> = mutableListOf(NormalSet(10.0), NormalSet(20.0))
        val sut = MachineWorkout(machine, sets)
        val want = NormalSet(20.0)

        val have = sut.getTopSet()

        assertEquals(want, have)
    }

    @Test
    fun getTopSetNoSets() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf())

        val have = sut.getTopSet()

        assertNull(have)
    }

    @Test
    fun getWeightDifferences() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(NormalSet(10.0), NormalSet(20.0)))
        val want = listOf(10.0)

        val have = sut.getWeightDifferences()

        assertEquals(want, have)
    }

    @Test
    fun getWeightDifferences2() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(NormalSet(10.0), NormalSet(20.0), NormalSet(15.0)))
        val want = listOf(10.0, -5.0)

        val have = sut.getWeightDifferences()

        assertEquals(want, have)
    }

    @Test
    fun getWeightDifferencesOtherSets() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(NormalSet(10.0), SpecialSet(20.0, "weird"), DropSet(15.0)))
        val want = listOf(10.0, -5.0)

        val have = sut.getWeightDifferences()

        assertEquals(want, have)
    }

    @Test
    fun getWeightDifferencesEmpty() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine)
        val want: List<Double> = listOf()

        val have = sut.getWeightDifferences()

        assertEquals(want, have)
    }

    @Test
    fun getWeightDifferencesOneElement() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(DropSet(10.0)))
        val want: List<Double> = listOf()

        val have = sut.getWeightDifferences()

        assertEquals(want, have)
    }

    @Test
    fun getWeightDifferencesPercent() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(NormalSet(10.0), NormalSet(20.0), NormalSet(15.0)))
        val want = listOf(100.0, -25.0)

        val have = sut.getRealWeightDifferencesPercent()

        assertEquals(want, have)
    }

    @Test
    fun getWeightDifferencesPercentEmpty() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine)
        val want: List<Double> = listOf()

        val have = sut.getRealWeightDifferencesPercent()

        assertEquals(want, have)
    }

    @Test
    fun getWeightDifferencesPercentOneElement() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(DropSet(10.0)))
        val want: List<Double> = listOf()

        val have = sut.getRealWeightDifferencesPercent()

        assertEquals(want, have)
    }

    @Test
    fun getWeightDifferencesWithBase() {
        val machine = Machine("Deadlifts")
        val sut = MachineWorkout(machine, mutableListOf(NormalSet(0.0), NormalSet(20.0)))
        val want = listOf(100.0) // Weight from 20 kg (Base) to 40 kg

        val have = sut.getRealWeightDifferencesPercent()

        assertEquals(want, have)
    }

    @Test
    fun addSet() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(NormalSet(10.0)))
        val want = mutableListOf(NormalSet(10.0), NormalSet(20.0))
        val have = sut.addSetByString("20").sets

        assertEquals(want, have)
    }

    @Test
    fun addSetDouble() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(NormalSet(10.0)))
        val want = mutableListOf(NormalSet(10.0), NormalSet(52.5))
        val have = sut.addSetByString("52.5").sets

        assertEquals(want, have)
    }

    @Test
    fun addMultipleSets() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(NormalSet(10.0), NormalSet(17.5)))
        val want = mutableListOf(NormalSet(10.0), NormalSet(17.5), NormalSet(20.0), NormalSet(27.5), NormalSet(22.5))
        val have = sut.addSetByString("20").addSetByString("27.5").addSetByString("22.5").sets

        assertEquals(want, have)
    }

    @Test
    fun addSetWithReps() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(NormalSet(10.0)))
        val want = mutableListOf(NormalSet(10.0), NormalSet(20.0, 10), NormalSet(27.5), NormalSet(22.5))
        val have = sut.addSetByString("10x 20").addSetByString("27.5").addSetByString("22.5").sets

        assertEquals(want, have)
    }

    @Test
    fun addSpecialSet() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf())
        val want = mutableListOf(SpecialSet(12.5, "ok"))
        val have = sut.addSetByString("12.5 (ok)").sets

        assertEquals(want, have)
    }

    @Test
    fun addSpecialSetWithReps() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf())
        val want = mutableListOf(SpecialSet(12.5, "what a bad set", 5))
        val have = sut.addSetByString("5x 12.5 (what a bad set)").sets

        assertEquals(want, have)
    }

    @Test
    fun addSpecialSetWithReps2() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf())
        val want = mutableListOf(SpecialSet(12.5, "what a bad set", 10))
        val have = sut.addSetByString("10x 12.5 (what a bad set)").sets

        assertEquals(want, have)
    }

    @Test
    fun addDropSet() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf())
        val want = mutableListOf(DropSet(12.5))
        val have = sut.addSetByString("Dropsatz von 12.5").sets

        assertEquals(want, have)
    }

    @Test
    fun addDifferentSets() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(SpecialSet(10.0, "strange")))
        val want = mutableListOf(
            SpecialSet(10.0, "strange"),
            NormalSet(20.0, 10),
            SpecialSet(27.5, "eh", 8),
            DropSet(30.0)
        )
        val have = sut.addSetByString("10x 20").addSetByString("8x 27.5 (eh)").addSetByString("Dropsatz von 30").sets

        assertEquals(want, have)
    }

    @Test
    fun addSetEmpty() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(NormalSet(10.0)))

        assertThrows(IllegalArgumentException::class.java) {
            sut.addSetByString("")
        }
    }

    @Test
    fun addSetInvalid1() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(NormalSet(10.0)))

        assertThrows(IllegalArgumentException::class.java) {
            sut.addSetByString("10x .25")
        }
    }

    @Test
    fun addSetInvalid2() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(NormalSet(10.0)))

        assertThrows(IllegalArgumentException::class.java) {
            sut.addSetByString("10x (info)")
        }
    }

    @Test
    fun addSetInvalid3() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(NormalSet(10.0)))

        assertThrows(IllegalArgumentException::class.java) {
            sut.addSetByString(".5")
        }
    }

    @Test
    fun addSetInvalid4() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(NormalSet(10.0)))

        assertThrows(IllegalArgumentException::class.java) {
            sut.addSetByString("10x")
        }
    }

    @Test
    fun addSetInvalid5() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(NormalSet(10.0)))

        assertThrows(IllegalArgumentException::class.java) {
            sut.addSetByString("10x 10 (")
        }
    }

    @Test
    fun addSetInvalid6() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(NormalSet(10.0)))

        assertThrows(IllegalArgumentException::class.java) {
            sut.addSetByString("10x 10 ()")
        }
    }

    @Test
    fun addSetInvalid7() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(NormalSet(10.0)))

        assertThrows(IllegalArgumentException::class.java) {
            sut.addSetByString("10x 10 )")
        }
    }

    @Test
    fun addSetInvalid8() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(machine, mutableListOf(NormalSet(10.0)))

        assertThrows(IllegalArgumentException::class.java) {
            sut.addSetByString("5 a")
        }
    }


    @Test
    fun getSetsRealWeight() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sets: MutableList<WorkoutSet> = mutableListOf(NormalSet(10.0), NormalSet(25.0))
        val sut = MachineWorkout(machine, sets)

        val have = sut.getSetsRealWeight()

        assertEquals(sets, have)
    }

    @Test
    fun getSetsRealWeight2() {
        val machine = Machine("Deadlifts", "Höhe 8")
        val sets: MutableList<WorkoutSet> = mutableListOf(NormalSet(10.0), NormalSet(25.0))
        val sut = MachineWorkout(machine, sets)
        val want: MutableList<WorkoutSet> = mutableListOf(NormalSet(30.0), NormalSet(45.0))

        val have = sut.getSetsRealWeight()

        assertEquals(want, have)
    }

    @Test
    fun getSetsRealWeight3() {
        val machine = Machine("Deadlifts", "Höhe 8")
        val sets: MutableList<WorkoutSet> = mutableListOf(NormalSet(10.0), NormalSet(25.0))
        val sut = MachineWorkout(machine, sets)

        val have = sut.getSetsRealWeight()

        // Sets should be untouched
        assertNotEquals(sets, have)
    }

    @Test
    fun getString() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut = MachineWorkout(
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
    fun getStringFull() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut =
            MachineWorkout(machine)
                .addSetByString("10 (strange)")
                .addSetByString("10x 20")
                .addSetByString("27.5 (eh)")
                .addSetByString("Dropsatz von 30")

        val want = "MTS Crunch (Höhe 8): 10 (strange), 10x 20, 27.5 (eh), Dropsatz von 30"

        val have = sut.toString()

        assertEquals(want, have)
    }

    @Test
    fun getVolume() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut =
            MachineWorkout(machine)
                .addSetByString("10 (strange)")
                .addSetByString("10x 20")
                .addSetByString("27.5 (eh)")
        val want = 650

        val have = sut.getVolume()

        assertEquals(want, have)
    }


    @Test
    fun getVolume2() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut =
            MachineWorkout(machine)
                .addSetByString("10 (strange)")
                .addSetByString("2x 20")
                .addSetByString("Dropsatz von 10")
        val want = 240

        val have = sut.getVolume()

        assertEquals(want, have)
    }

    @Test
    fun getVolume3() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val sut =
            MachineWorkout(
                machine, mutableListOf(
                    SpecialSet(10.0, "strange"),
                    NormalSet(20.0, 10),
                    SpecialSet(27.5, "eh", 8),
                    DropSet(30.0)
                )
            )
        val want = 780

        val have = sut.getVolume()

        assertEquals(want, have)
    }
}