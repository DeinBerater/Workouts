package de.deinberater.workouts

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class WorkoutTest {


    @Test
    fun ctorDateString() {
        val workout = Workout(WorkoutType.Legs, "05.02.24")
        val want = LocalDate.of(2024, 2, 5)

        val have = workout.date

        assertEquals(want, have)
    }

    @Test
    fun ctorDateNoString() {
        val workout = Workout(WorkoutType.Legs, LocalDate.of(2024, 2, 5))
        val want = LocalDate.of(2024, 2, 5)

        val have = workout.date

        assertEquals(want, have)
    }

    @Test
    fun ctorDateDifferent() {
        val workout = Workout(WorkoutType.Legs, LocalDate.of(2024, 1, 5))
        val want = LocalDate.of(2024, 2, 5)

        val have = workout.date

        assertNotEquals(want, have)
    }

    @Test
    fun ctorInvalidDate() {
        assertThrows(IllegalArgumentException::class.java) {
            Workout(WorkoutType.Legs, "29.10.99")
        }
    }

    @Test
    fun ctorInvalidDate2() {
        assertThrows(IllegalArgumentException::class.java) {
            Workout(WorkoutType.Legs, "BananaDate")
        }
    }

    @Test
    fun addMachineWorkoutInvalid1() {
        val workout = Workout(WorkoutType.Legs, "10.10.23")

        assertThrows(IllegalArgumentException::class.java) {
            workout.addMachineWorkout("abc: ()")
        }
    }

    @Test
    fun addMachineWorkoutInvalid2() {
        val workout = Workout(WorkoutType.Legs, "10.10.23")

        assertThrows(IllegalArgumentException::class.java) {
            workout.addMachineWorkout("MTS Crunch (Höhe 8): Banana")
        }
    }

    @Test
    fun addMachineWorkoutInvalid3() {
        val workout = Workout(WorkoutType.Legs, "10.10.23")

        assertThrows(IllegalArgumentException::class.java) {
            workout.addMachineWorkout("MTS Crunch (Höhe 8): 10x")
        }
    }

    @Test
    fun addMachineWorkoutInvalid4() {
        val workout = Workout(WorkoutType.Legs, "10.10.23")

        assertThrows(IllegalArgumentException::class.java) {
            workout.addMachineWorkout("MTS Crunch (Höhe 8): 10x (Info), 10")
        }
    }

    @Test
    fun addMachineWorkoutInvalid5() {
        val workout = Workout(WorkoutType.Legs, "10.10.23")

        assertThrows(IllegalArgumentException::class.java) {
            workout.addMachineWorkout("MTS Crunch (Höhe 8): 10 (Info), 10, Dropsatz von 10x")
        }
    }

    @Test
    fun addMachineWorkoutInvalid6() {
        val workout = Workout(WorkoutType.Legs, "10.10.23")

        assertThrows(IllegalArgumentException::class.java) {
            workout.addMachineWorkout("MTS Crunch (Höhe 8): 10 (Info), 10, Dropsatz von Banana")
        }
    }

    @Test
    fun addMachineWorkoutInvalid7() {
        val workout = Workout(WorkoutType.Legs, "10.10.23")

        assertThrows(IllegalArgumentException::class.java) {
            workout.addMachineWorkout("MTS Crunch: 1 2 3 a 5 6")
        }
    }

    @Test
    fun addMachineWorkoutInvalid8() {
        val workout = Workout(WorkoutType.Legs, "10.10.23")

        assertThrows(IllegalArgumentException::class.java) {
            workout.addMachineWorkout("MTS Crunch (: 1 2 3 4 5 6")
        }
    }

    @Test
    fun addMachineWorkoutEmpty() {
        val workout = Workout(WorkoutType.Legs, "10.10.23")

        assertThrows(IllegalArgumentException::class.java) {
            workout.addMachineWorkout("")
        }

    }

    @Test
    fun addMachineWorkoutEmptySets() {
        val workout = Workout(WorkoutType.Legs, "10.10.23")
        val want = Machine("Blaues Teil")

        val have = workout.addMachineWorkout("Blaues Teil").machines.first().machine

        assertTrue(want.isEqual(have))
    }

    @Test
    fun addMachineWorkoutEmptySets2() {
        val workout = Workout(WorkoutType.BellyShoulders, "10.10.23")
        val want = Machine("Blaues Teil", "meh")

        val have = workout.addMachineWorkout("Blaues Teil (meh)").machines.first().machine

        assertTrue(want.isEqual(have))
//        assertEquals(want, have)
    }

    @Test
    fun addMachineWorkout1() {
        val workout = Workout(WorkoutType.Legs, "10.10.23")
        val wantMachine = Machine("MTS Crunch", "Höhe: 8")
        val wantSets = listOf(NormalSet(20.0), NormalSet(30.0), NormalSet(40.0, 10), DropSet(50.0))

        val have = workout.addMachineWorkout("MTS Crunch (Höhe: 8): 20, 30, 10x 40, Dropsatz von 50").machines.first()

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }

    @Test
    fun addMachineWorkout2() {
        val workout = Workout(WorkoutType.Legs, "10.10.23")
        val wantMachine = Machine("MTS Crunch", "Höhe: 8")
        val wantSets = listOf(NormalSet(20.0), NormalSet(30.0), NormalSet(40.0, 10), DropSet(50.0))

        val have = workout.addMachineWorkout("MTS Crunch (Höhe: 8): 20 30, 10x 40, Dropsatz von 50").machines.first()

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }

    @Test
    fun addMachineWorkout3() {
        val workout = Workout(WorkoutType.Back, "03.02.23")
        val wantMachine = Machine("Latzug", "not good")
        val wantSets = listOf(
            NormalSet(40.0),
            NormalSet(50.0, 10),
            NormalSet(60.0, 9),
            NormalSet(65.0),
            DropSet(65.0),
            NormalSet(40.0)
        )

        val have =
            workout.addMachineWorkout("Latzug (not good): 40, 10x 50, 9x 60, 65, Dropsatz von 65, 40").machines.first()

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }


    @Test
    fun addMachineWorkoutSimple() {
        val workout = Workout(WorkoutType.Back, "03.02.23")
        val wantMachine = Machine("Latzug", "not good")
        val wantSets = listOf(
            NormalSet(40.0),
            NormalSet(65.0),
            NormalSet(40.0)
        )

        val have =
            workout.addMachineWorkout("Latzug (not good): 40 65 40").machines.first()

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }

    @Test
    fun addMachineWorkoutSimpler() {
        val workout = Workout(WorkoutType.Back, "03.02.23")
        val wantMachine = Machine("Lateral Raise Kabelzug", "Höhe: 5")
        val wantSets = listOf(
            NormalSet(5.0),
            NormalSet(5.0),
            NormalSet(5.0)
        )

        val have =
            workout.addMachineWorkout("Lateral Raise Kabelzug (Höhe: 5): 5 5 5").machines.first()

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }

    @Test
    fun addMachineWorkoutBrackets() {
        val workout = Workout(WorkoutType.Back, "03.02.23")
        val wantMachine = Machine("Lateral Raise Kabelzug", "Höhe: 5")
        val wantSets = listOf(
            NormalSet(55.0),
            SpecialSet(60.0, "eher meh"),
            NormalSet(5.0)
        )

        val have =
            workout.addMachineWorkout("Lateral Raise Kabelzug (Höhe: 5): 55, 60 (eher meh), 5").machines.first()

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }

    @Test
    fun addMachineWorkoutBrackets2() {
        val workout = Workout(WorkoutType.Back, "03.02.23")
        val wantMachine = Machine("Lateral Raise Kabelzug", "Höhe: 5")
        val wantSets = listOf(
            NormalSet(55.0),
            SpecialSet(60.0, "Höhe: 4"),
            NormalSet(5.0)
        )

        val have =
            workout.addMachineWorkout("Lateral Raise Kabelzug (Höhe: 5): 55, 60 (Höhe: 4), 5").machines.first()

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }

    @Test
    fun addMachineWorkoutNoDetails() {
        val workout = Workout(WorkoutType.Back, "03.02.23")
        val wantMachine = Machine("Lateral Raise Kabelzug")
        val wantSets = listOf(
            NormalSet(55.0),
            SpecialSet(60.0, "Höhe: 4"),
            NormalSet(5.0)
        )

        val have =
            workout.addMachineWorkout("Lateral Raise Kabelzug: 55, 60 (Höhe: 4), 5").machines.first()

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }

    @Test
    fun addMachineWorkoutSpecialMultipleSets() {
        val workout = Workout(WorkoutType.Chest, "03.02.23")
        val wantMachine = Machine("Cable Flies")
        val wantSets = listOf(
            SpecialSet(10.0, "Höhe 4", 10),
            SpecialSet(15.0, "Höhe 4", 5),
            SpecialSet(10.0, "Höhe 14"),
            SpecialSet(15.0, "Höhe 14", 9),
            SpecialSet(15.0, "Höhe 14", 7),
        )

        val have =
            workout.addMachineWorkout("Cable Flies: Höhe 4: 10x 10, 5x 15, Höhe 14: 10, 9x 15, 7x 15").machines.first()

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)

    }

    @Test
    fun addMachineWorkoutSpecialMultipleSets2() {
        val workout = Workout(WorkoutType.Chest, "03.02.23")
        val wantMachine = Machine("Cable Flies")
        val wantSets = listOf(
            SpecialSet(10.0, "Höhe 5", 10),
            SpecialSet(15.0, "Höhe 4", 5),
            SpecialSet(10.0, "Höhe 14"),
            SpecialSet(15.0, "Höhe 14", 9),
            SpecialSet(15.0, "Höhe 14", 7),
        )

        val have =
            workout.addMachineWorkout("Cable Flies: Höhe 4: 10x 10 (Höhe 5), 5x 15, Höhe 14: 10, 9x 15, 7x 15").machines.first()

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)

    }


    @Test
    fun workoutFromString1() {
        val string = "Tag 1: 05.02.24"
        val want = LocalDate.of(2024, 2, 5)

        val have = Workout(WorkoutType.Legs, string).date

        assertEquals(want, have)
    }

    @Test
    fun workoutFromString2() {
        val string = "05.02.24"
        val want = LocalDate.of(2024, 2, 5)
        val wantDetails = null
        val sut = Workout(WorkoutType.Legs, string)

        val have = sut.date
        val haveDetails = sut.specialInfo

        assertEquals(want, have)
        assertEquals(wantDetails, haveDetails)
    }

    @Test
    fun workoutFromString3() {
        val string = "05.02.24 "
        val want = LocalDate.of(2024, 2, 5)

        val have = Workout(WorkoutType.Legs, string).date

        assertEquals(want, have)
    }

    @Test
    fun workoutFromString4() {
        val string = "05.02.24 (a)"
        val want = LocalDate.of(2024, 2, 5)

        val have = Workout(WorkoutType.Legs, string).date

        assertEquals(want, have)
    }

    @Test
    fun workoutFromString5() {
        val string = "05.02.24:a"
        val want = LocalDate.of(2024, 2, 5)
        val wantDetails = "a"
        val sut = Workout(WorkoutType.Legs, string)

        val have = sut.date
        val haveDetails = sut.specialInfo

        assertEquals(want, have)
        assertEquals(wantDetails, haveDetails)
    }


    @Test
    fun workoutFromString6() {
        val string = "Tag Banane:05.02.24 "
        val want = LocalDate.of(2024, 2, 5)
        val wantDetails = null
        val sut = Workout(WorkoutType.Legs, string)


        val have = sut.date
        val haveDetails = sut.specialInfo

        assertEquals(want, have)
        assertEquals(wantDetails, haveDetails)
    }

    @Test
    fun workoutFromString7() {
        val string = "Tag 1:05.02.24 (special shit)"
        val wantDate = LocalDate.of(2024, 2, 5)
        val wantDetails = "special shit"
        val sut = Workout(WorkoutType.Legs, string)

        val haveDate = sut.date
        val haveDetails = sut.specialInfo

        assertEquals(wantDate, haveDate)
        assertEquals(wantDetails, haveDetails)
    }

    @Test
    fun workoutFromString8() {
        val string = "Tag 1:05.02.24: special shit"
        val wantDate = LocalDate.of(2024, 2, 5)
        val wantDetails = "special shit"
        val sut = Workout(WorkoutType.Legs, string)

        val haveDate = sut.date
        val haveDetails = sut.specialInfo

        assertEquals(wantDate, haveDate)
        assertEquals(wantDetails, haveDetails)
    }

    @Test
    fun workoutFromString9() {
        val string = "Tag 1: 05.02.24:special shit"
        val wantDate = LocalDate.of(2024, 2, 5)
        val wantDetails = "special shit"
        val sut = Workout(WorkoutType.Legs, string)

        val haveDate = sut.date
        val haveDetails = sut.specialInfo

        assertEquals(wantDate, haveDate)
        assertEquals(wantDetails, haveDetails)
    }

    @Test
    fun workoutFromStringInvalid1() {
        val string = "Tag 1 05.02.24 (special shit)"

        assertThrows(IllegalArgumentException::class.java) {
            Workout(WorkoutType.Legs, string)
        }
    }

    @Test
    fun workoutFromStringInvalid2() {
        val string = "Tag 1 05.02.24: special shit"

        assertThrows(IllegalArgumentException::class.java) {
            Workout(WorkoutType.Legs, string)
        }
    }

    @Test
    fun workoutFromStringInvalid3() {
        val string = "Tag 1: 05.02.24 special shit"

        assertThrows(IllegalArgumentException::class.java) {
            Workout(WorkoutType.Legs, string)
        }
    }

    @Test
    fun workoutFromStringInvalid4() {
        val string = "Tag 1: 05.02. 24: special shit"

        assertThrows(IllegalArgumentException::class.java) {
            Workout(WorkoutType.Legs, string)
        }
    }

    @Test
    fun workoutFromStringEmpty() {
        val string = ""

        assertThrows(IllegalArgumentException::class.java) {
            Workout(WorkoutType.Legs, string)
        }
    }

    @Test
    fun addCompleteWorkout() {
        val string = """
            Tag 58: 05.02.24
            Row: 22.5, 10x 32.5, 10x 37.5, 10x 42.5, Dropsatz von 45
            MTS Armstrecker (Höhe 6): 10, 10x 15, 8x 17.5, 7x 20, 7x 17.5
            Shrugs Trap Bar (ohne Bar): 20 40, 9x 50, 9x 60, 10x 40
            Skull Crusher (Gewicht gesamt): 2.5, 10x 5, 6x 7.5, 8x 5
            Pulldown: 35 42.5, 10x 50, 10x 57.5, 45
            Rear Deltoid: 20, 9x 30, 9x 27.5, 9x 25
        """.trimIndent()

        val date = LocalDate.of(2024, 2, 5)

        val machineWorkouts = mutableListOf(
            MachineWorkout(
                Machine("Row"),
                mutableListOf(
                    NormalSet(22.5),
                    NormalSet(32.5, 10),
                    NormalSet(37.5, 10),
                    NormalSet(42.5, 10),
                    DropSet(45.0)
                )
            ),
            MachineWorkout(
                Machine("MTS Armstrecker", "Höhe 6"),
                mutableListOf(
                    NormalSet(10.0),
                    NormalSet(15.0, 10),
                    NormalSet(17.5, 8),
                    NormalSet(20.0, 7),
                    NormalSet(17.5, 7)
                )
            ),
            MachineWorkout(
                Machine("Shrugs Trap Bar", "ohne Bar"),
                mutableListOf(
                    NormalSet(20.0),
                    NormalSet(40.0),
                    NormalSet(50.0, 9),
                    NormalSet(60.0, 9),
                    NormalSet(40.0, 10)
                )
            ),
            MachineWorkout(
                Machine("Skull Crusher", "Gewicht gesamt"),
                mutableListOf(
                    NormalSet(2.5),
                    NormalSet(5.0, 10),
                    NormalSet(7.5, 6),
                    NormalSet(5.0, 8),
                )
            ),
            MachineWorkout(
                Machine("Pulldown"),
                mutableListOf(
                    NormalSet(35.0),
                    NormalSet(42.5),
                    NormalSet(50.0, 10),
                    NormalSet(57.5, 10),
                    NormalSet(45.0),
                )
            ),
            MachineWorkout(
                Machine("Rear Deltoid"),
                mutableListOf(
                    NormalSet(20.0),
                    NormalSet(30.0, 9),
                    NormalSet(27.5, 9),
                    NormalSet(25.0, 9),
                )
            )
        )
        val want = Workout(WorkoutType.Back, date, machineWorkouts)

        val have = Workout(WorkoutType.Back, string)

        assertEquals(want, have)
    }

    @Test
    fun convertToString() {
        val string = """
            Tag 58: 05.02.24
        """.trimIndent()
        val want = "05.02.24"

        val have = Workout(WorkoutType.Legs, string).toString()

        assertEquals(want, have)
    }

    @Test
    fun convertToString2() {
        val string = """
            Tag 58: 05.02.24 (Nice)
        """.trimIndent()
        val want = "05.02.24: Nice"

        val have = Workout(WorkoutType.Legs, string).toString()

        assertEquals(want, have)
    }

    @Test
    fun convertToString3() {
        val string = """
            Tag 58: 05.02.24:Nice 
        """.trimIndent()
        val want = "05.02.24: Nice"

        val have = Workout(WorkoutType.Legs, string).toString()

        assertEquals(want, have)
    }

    @Test
    fun addByStringAndDestructure() {
        val string = """
            05.02.24: Nice Info
            Row: 22.5, 10x 32.5, 10x 37.5, 10x 42.5, Dropsatz von 45
            MTS Armstrecker (Höhe 6): 10, 10x 15, 8x 17.5, 7x 20, 7x 17.5
            Shrugs Trap Bar (ohne Bar): 20, 40, 9x 50, 9x 60, 10x 40
            Skull Crusher (Gewicht gesamt): 2.5, 10x 5, 6x 7.5, 8x 5
            Pulldown: 35, 42.5, 10x 50, 10x 57.5, 45
            Rear Deltoid: 20, 9x 30, 9x 27.5, 9x 25
        """.trimIndent()

        val sut = Workout(WorkoutType.Back, string)

        val have = sut.toString()

        assertEquals(string, have)
    }

    @Test
    fun addByStringOneMistake() {
        val string = """
            05.02.24: Nice Info
            Row: 22.5, 10x 32.5, 10x 37.5, 10x 42.5, Dropsatz von 45
            MTS Armstrecker (Höhe 6): 10, 10x 15, 8x 17.5, 7x 20, 7x 17.5
            Shrugs Trap Bar (ohne Bar): 20, 40x 9x 50, 9x 60, 10x 40
            Skull Crusher (Gewicht gesamt): 2.5, 10x 5, 6x 7.5, 8x 5
            Pulldown: 35, 42.5, 10x 50, 10x 57.5, 45
            Rear Deltoid: 20, 9x 30, 9x 27.5, 9x 25
        """.trimIndent()

        assertThrows(IllegalArgumentException::class.java) {
            Workout(WorkoutType.Back, string)
        }
    }
}