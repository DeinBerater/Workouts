package workoutcreator

import de.deinberater.workouts.Exercise
import de.deinberater.workouts.Workout
import de.deinberater.workouts.WorkoutType
import de.deinberater.workouts.machines.Machine
import de.deinberater.workouts.workoutcreator.DeinBeratersWorkoutCreator
import de.deinberater.workouts.workoutsets.DropSet
import de.deinberater.workouts.workoutsets.NormalSet
import de.deinberater.workouts.workoutsets.SpecialSet
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class DeinBeratersWorkoutCreatorTest {
    private val sut = DeinBeratersWorkoutCreator()

    @Test
    fun createWorkoutDateString() {
        val workout = sut.createWorkout("05.02.24")
        val want = LocalDate.of(2024, 2, 5)

        val have = workout.date

        assertEquals(want, have)
    }

    @Test
    fun createWorkoutInvalidDate() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createWorkout("29.10.99")
        }
    }

    @Test
    fun createWorkoutInvalidDate2() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createWorkout("BlueberriesWithMangoPlease")
        }
    }


    @Test
    fun createExerciseInvalid1() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createExercise("abc: ()")
        }
    }


    @Test
    fun createExerciseInvalid2() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createExercise("MTS Crunch (8): Say Cheese")
        }
    }


    @Test
    fun createExerciseInvalid3() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createExercise("MTS Crunch (8): 10x")
        }
    }


    @Test
    fun createExerciseInvalid4() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createExercise("MTS Crunch (8): 10x (Info), 10")
        }
    }


    @Test
    fun createExerciseInvalid5() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createExercise("MTS Crunch (8): 10 (Info), 10, Dropsatz von 10x")
        }
    }

    @Test
    fun createExerciseInvalid6() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createExercise("MTS Crunch (Höhe 8): 10 (Info), 10, Dropsatz von Banana")
        }
    }

    @Test
    fun createExerciseInvalid7() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createExercise("MTS Crunch: 1 2 3 a 5 6")
        }
    }

    @Test
    fun createExerciseInvalid8() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createExercise("MTS Crunch (: 1 2 3 4 5 6")
        }
    }

    @Test
    fun createExerciseEmpty() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createExercise("")
        }
    }

    @Test
    fun createExerciseEmptySets() {
        val want = Machine("Blaues Teil")

        val have = sut.createExercise("Blaues Teil").machine

        assertTrue(want.isEqual(have))
    }

    @Test
    fun createExerciseEmptySets2() {
        val want = Machine("Blaues Teil")

        val have = sut.createExercise("Blaues Teil (meh)").machine

        assertTrue(want.same(have))
    }

    @Test
    fun createExerciseEmptySets3() {
        val want = Machine("Blaues Teil")

        val have = sut.createExercise("Blaues Teil (meh)").machine

        assertFalse(want.isEqual(have))
    }

    @Test
    fun createExerciseEmptySets4() {
        val want = Machine("Blaues Teil", "otherDetails")

        val have = sut.createExercise("Blaues Teil (meh)").machine

        assertFalse(want.isEqual(have))
    }

    @Test
    fun createExerciseEmptySets5() {
        val want = Machine("Blaues Teil", "meh")

        val have = sut.createExercise("Blaues Teil (meh)").machine

        assertTrue(want.same(have))
    }

    @Test
    fun createExercise1() {
        val wantMachine = Machine("MTS Crunch", "8")
        val wantSets = listOf(NormalSet(20.0), NormalSet(30.0), NormalSet(40.0, 10), DropSet(50.0))

        val have = sut.createExercise("MTS Crunch (8): 20, 30, 10x 40, Dropsatz von 50")

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }


    @Test
    fun createExercise2() {
        val wantMachine = Machine("MTS Crunch", "8")
        val wantSets = listOf(NormalSet(20.0), DropSet(30.0), NormalSet(40.0, 10), DropSet(50.0))

        val have = sut.createExercise("MTS Crunch (8): 20, Dropsatz von 30, 10x 40, Dropsatz von 50")

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }

    @Test
    fun createExercise3() {
        val wantMachine = Machine("Latzug", "not good")
        val wantSets = listOf(
            NormalSet(40.0),
            NormalSet(50.0, 10),
            NormalSet(60.0, 9),
            NormalSet(65.0),
            DropSet(65.0),
            NormalSet(40.0)
        )

        val have = sut.createExercise("Latzug (not good): 40, 10x 50, 9x 60, 65, Dropsatz von 65, 40")

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }


    @Test
    fun createExerciseSimple() {
        val wantMachine = Machine("Latzug", "not good")
        val wantSets = listOf(
            NormalSet(40.0),
            NormalSet(65.0),
            NormalSet(40.0)
        )

        val have = sut.createExercise("Latzug (not good): 40 65 40")

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }

    @Test
    fun createExerciseSimple2() {
        val wantMachine = Machine("Seated calf")
        val wantSets = listOf(
            NormalSet(25.0),
            NormalSet(30.0),
            NormalSet(30.0),
            NormalSet(30.0),
            NormalSet(30.0),
            NormalSet(30.0),
        )

        val have = sut.createExercise("Seated calf: 25 30 30, 30 30 30")

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }

    @Test
    fun createExerciseSimple3() {
        val wantMachine = Machine("Seated calf")
        val wantSets = listOf(
            NormalSet(30.0),
            NormalSet(30.0),
            NormalSet(30.0),
            NormalSet(30.0),
            NormalSet(30.0),
            NormalSet(30.0),
        )

        val have = sut.createExercise("Seated calf: 30, 30, 30, 30, 30 30")

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }

    @Test
    fun createExerciseSimple4() {
        val wantMachine = Machine("Glute")
        val wantSets = listOf(
            NormalSet(27.5),
            NormalSet(35.0),
            NormalSet(35.0),
        )

        val have = sut.createExercise("Glute: 27.5 35 35")

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }

    @Test
    fun createExerciseSimpler() {
        val wantMachine = Machine("Lateral Raise Kabelzug", "Höhe: 5")
        val wantSets = listOf(
            NormalSet(5.0),
            NormalSet(5.0),
            NormalSet(5.0),
        )

        val have = sut.createExercise("Lateral Raise Kabelzug (Höhe: 5): 5 5 5")

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }

    @Test
    fun createExerciseBrackets() {
        val wantMachine = Machine("Lateral Raise Kabelzug", "Höhe: 5")
        val wantSets = listOf(
            NormalSet(55.0),
            SpecialSet(60.0, "eher meh"),
            NormalSet(5.0)
        )

        val have = sut.createExercise("Lateral Raise Kabelzug (Höhe: 5): 55, 60 (eher meh), 5")

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }

    @Test
    fun createExerciseBrackets2() {
        val wantMachine = Machine("Lateral Raise Kabelzug", "Höhe: 5")
        val wantSets = listOf(
            NormalSet(55.0),
            SpecialSet(60.0, "Höhe: 4"),
            NormalSet(5.0)
        )

        val have = sut.createExercise("Lateral Raise Kabelzug (Höhe: 5): 55, 60 (Höhe: 4), 5")

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }

    @Test
    fun createExerciseNoDetails() {
        val wantMachine = Machine("Lateral Raise Kabelzug", "Höhe: 5")
        val wantSets = listOf(
            NormalSet(55.0),
            SpecialSet(60.0, "Höhe: 4"),
            NormalSet(5.0)
        )

        val have = sut.createExercise("Lateral Raise Kabelzug (Höhe: 5): 55, 60 (Höhe: 4), 5")

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }


    @Test
    fun createExerciseSpecialMultipleSets() {
        val wantMachine = Machine("Cable Flies")
        val wantSets = listOf(
            SpecialSet(10.0, "Höhe 4", 10),
            SpecialSet(15.0, "Höhe 4", 5),
            SpecialSet(10.0, "Höhe 14"),
            SpecialSet(15.0, "Höhe 14", 9),
            SpecialSet(15.0, "Höhe 14", 7),
        )

        val have = sut.createExercise("Cable Flies: Höhe 4: 10x 10, 5x 15, Höhe 14: 10, 9x 15, 7x 15")

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }

    @Test
    fun createExerciseSpecialMultipleSets2() {
        val wantMachine = Machine("Cable Flies")
        val wantSets = listOf(
            SpecialSet(10.0, "Höhe 5", 10),
            SpecialSet(15.0, "Höhe 4", 5),
            SpecialSet(10.0, "Höhe 14"),
            SpecialSet(15.0, "Höhe 14", 9),
            SpecialSet(15.0, "Höhe 14", 7),
        )

        val have = sut.createExercise("Cable Flies: Höhe 4: 10x 10 (Höhe 5), 5x 15, Höhe 14: 10, 9x 15, 7x 15")

        val haveMachine = have.machine
        val haveSets = have.sets

        assertTrue(wantMachine.isEqual(haveMachine))
        assertEquals(wantSets, haveSets)
    }


    @Test
    fun workoutFromString1() {
        val string = "Tag 1: 05.02.24"
        val want = LocalDate.of(2024, 2, 5)

        val have = sut.createWorkout(string).date
        assertEquals(want, have)
    }

    @Test
    fun workoutFromString2() {
        val string = "05.02.24"
        val want = LocalDate.of(2024, 2, 5)

        val workout = sut.createWorkout(string)

        val have = workout.date
        val haveDetails = workout.specialInfo

        assertEquals(want, have)
        assertNull(haveDetails)
    }

    @Test
    fun workoutFromString3() {
        val string = "05.02.24 "
        val want = LocalDate.of(2024, 2, 5)

        val have = sut.createWorkout(string).date
        assertEquals(want, have)
    }

    @Test
    fun workoutFromString4() {
        val string = "05.02.24 (a)"
        val want = LocalDate.of(2024, 2, 5)

        val have = sut.createWorkout(string).date
        assertEquals(want, have)
    }


    @Test
    fun workoutFromString5() {
        val string = "05.02.24:a"
        val want = LocalDate.of(2024, 2, 5)
        val wantDetails = "a"

        val workout = sut.createWorkout(string)

        val have = workout.date
        val haveDetails = workout.specialInfo

        assertEquals(want, have)
        assertEquals(wantDetails, haveDetails)
    }


    @Test
    fun workoutFromString6() {
        val string = "Tag Banane:05.02.24 "
        val want = LocalDate.of(2024, 2, 5)
        val wantDetails = null

        val workout = sut.createWorkout(string)

        val have = workout.date
        val haveDetails = workout.specialInfo

        assertEquals(want, have)
        assertEquals(wantDetails, haveDetails)
    }

    @Test
    fun workoutFromString7() {
        val string = "Tag 1:05.02.24 (special shit)"
        val wantDate = LocalDate.of(2024, 2, 5)
        val wantDetails = "special shit"

        val workout = sut.createWorkout(string)

        val haveDate = workout.date
        val haveDetails = workout.specialInfo

        assertEquals(wantDate, haveDate)
        assertEquals(wantDetails, haveDetails)
    }

    @Test
    fun workoutFromString8() {
        val string = "Tag 1:05.02.24: special shit"
        val wantDate = LocalDate.of(2024, 2, 5)
        val wantDetails = "special shit"

        val workout = sut.createWorkout(string)

        val haveDate = workout.date
        val haveDetails = workout.specialInfo

        assertEquals(wantDate, haveDate)
        assertEquals(wantDetails, haveDetails)
    }

    @Test
    fun workoutFromString9() {
        val string = "Tag 1: 05.02.24:special shit"
        val wantDate = LocalDate.of(2024, 2, 5)
        val wantDetails = "special shit"

        val workout = sut.createWorkout(string)

        val haveDate = workout.date
        val haveDetails = workout.specialInfo

        assertEquals(wantDate, haveDate)
        assertEquals(wantDetails, haveDetails)
    }

    @Test
    fun workoutFromStringInvalid1() {
        val string = "Tag 1 05.02.24 (special shit)"

        assertThrows(IllegalArgumentException::class.java) {
            sut.createWorkout(string)
        }
    }

    @Test
    fun workoutFromStringInvalid2() {
        val string = "Tag 1 05.02.24: special shit"

        assertThrows(IllegalArgumentException::class.java) {
            sut.createWorkout(string)
        }
    }

    @Test
    fun workoutFromStringInvalid3() {
        val string = "Tag 1: 05.02.24 special shit"

        assertThrows(IllegalArgumentException::class.java) {
            sut.createWorkout(string)
        }
    }

    @Test
    fun workoutFromStringInvalid4() {
        val string = "Tag 1: 05.02. 24: special shit"

        assertThrows(IllegalArgumentException::class.java) {
            sut.createWorkout(string)
        }
    }

    @Test
    fun workoutFromStringEmpty() {
        val string = ""

        assertThrows(IllegalArgumentException::class.java) {
            sut.createWorkout(string)
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

        val exercises = mutableListOf(
            Exercise(
                Machine("Row"),
                mutableListOf(
                    NormalSet(22.5),
                    NormalSet(32.5, 10),
                    NormalSet(37.5, 10),
                    NormalSet(42.5, 10),
                    DropSet(45.0)
                )
            ),
            Exercise(
                Machine("MTS Armstrecker", "Höhe 6"),
                mutableListOf(
                    NormalSet(10.0),
                    NormalSet(15.0, 10),
                    NormalSet(17.5, 8),
                    NormalSet(20.0, 7),
                    NormalSet(17.5, 7)
                )
            ),
            Exercise(
                Machine("Shrugs Trap Bar", "ohne Bar"),
                mutableListOf(
                    NormalSet(20.0),
                    NormalSet(40.0),
                    NormalSet(50.0, 9),
                    NormalSet(60.0, 9),
                    NormalSet(40.0, 10)
                )
            ),
            Exercise(
                Machine("Skull Crusher", "Gewicht gesamt"),
                mutableListOf(
                    NormalSet(2.5),
                    NormalSet(5.0, 10),
                    NormalSet(7.5, 6),
                    NormalSet(5.0, 8),
                )
            ),
            Exercise(
                Machine("Pulldown"),
                mutableListOf(
                    NormalSet(35.0),
                    NormalSet(42.5),
                    NormalSet(50.0, 10),
                    NormalSet(57.5, 10),
                    NormalSet(45.0),
                )
            ),
            Exercise(
                Machine("Rear Deltoid"),
                mutableListOf(
                    NormalSet(20.0),
                    NormalSet(30.0, 9),
                    NormalSet(27.5, 9),
                    NormalSet(25.0, 9),
                )
            )
        )

        val want = Workout(WorkoutType.Unknown, date, exercises)

        val have = sut.createWorkout(string)

        assertEquals(want, have)
    }


    @Test
    fun convertToString() {
        val string = """
            Tag 58: 05.02.24
        """.trimIndent()
        val want = "05.02.24"

        val have = sut.createWorkout(string).toString()

        assertEquals(want, have)
    }

    @Test
    fun convertToString2() {
        val string = """
            Tag 58: 05.02.24 (Nice)
        """.trimIndent()
        val want = "05.02.24: Nice"

        val have = sut.createWorkout(string).toString()

        assertEquals(want, have)
    }

    @Test
    fun convertToString3() {
        val string = """
            Tag 58: 05.02.24:Nice
        """.trimIndent()
        val want = "05.02.24: Nice"

        val have = sut.createWorkout(string).toString()

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

        val workout = sut.createWorkout(string)

        val have = workout.toString()

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
            sut.createWorkout(string)
        }
    }

    @Test
    fun createSet() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val exercise = Exercise(machine, mutableListOf(NormalSet(10.0)))
        val want = mutableListOf(NormalSet(10.0), NormalSet(20.0))

        exercise.sets.add(sut.createWorkoutSet("20"))
        val have = exercise.sets

        assertEquals(want, have)
    }


    @Test
    fun createSetDouble() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val exercise = Exercise(machine, mutableListOf(NormalSet(10.0)))
        val want = mutableListOf(NormalSet(10.0), NormalSet(52.5))

        exercise.sets.add(sut.createWorkoutSet("52.5"))
        val have = exercise.sets

        assertEquals(want, have)
    }

    @Test
    fun addMultipleSets() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val exercise = Exercise(machine, mutableListOf(NormalSet(10.0)))
        val want = mutableListOf(NormalSet(10.0), NormalSet(17.5), NormalSet(20.0), NormalSet(27.5), NormalSet(22.5))

        exercise.sets.addAll(
            listOf(
                sut.createWorkoutSet("17.5"),
                sut.createWorkoutSet("20"),
                sut.createWorkoutSet("27.5"),
                sut.createWorkoutSet("22.5")
            )
        )
        val have = exercise.sets

        assertEquals(want, have)
    }

    @Test
    fun addSetWithReps() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val exercise = Exercise(machine, mutableListOf(NormalSet(10.0)))
        val want = mutableListOf(NormalSet(10.0), NormalSet(20.0, 10), NormalSet(27.5), NormalSet(22.5))

        exercise.sets.addAll(
            listOf(
                sut.createWorkoutSet("10x 20"),
                sut.createWorkoutSet("27.5"),
                sut.createWorkoutSet("22.5")
            )
        )
        val have = exercise.sets

        assertEquals(want, have)
    }

    @Test
    fun addSpecialSet() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val exercise = Exercise(machine, mutableListOf())
        val want = mutableListOf(SpecialSet(12.5, "ok"))

        exercise.sets.add(sut.createWorkoutSet("12.5 (ok)"))
        val have = exercise.sets

        assertEquals(want, have)
    }

    @Test
    fun addSpecialSetWithReps() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val exercise = Exercise(machine, mutableListOf())
        val want = mutableListOf(SpecialSet(12.5, "what a bad set", 5))

        exercise.sets.add(sut.createWorkoutSet("5x 12.5 (what a bad set)"))
        val have = exercise.sets

        assertEquals(want, have)
    }

    @Test
    fun addSpecialSetWithReps2() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val exercise = Exercise(machine, mutableListOf())
        val want = mutableListOf(SpecialSet(12.5, "what a bad set", 10))

        exercise.sets.add(sut.createWorkoutSet("10x 12.5 (what a bad set)"))
        val have = exercise.sets

        assertEquals(want, have)
    }


    @Test
    fun addDropSet() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val exercise = Exercise(machine, mutableListOf())
        val want = mutableListOf(DropSet(12.5))

        exercise.sets.add(sut.createWorkoutSet("Dropsatz von 12.5"))
        val have = exercise.sets

        assertEquals(want, have)
    }

    @Test
    fun addDifferentSets() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val exercise = Exercise(machine, mutableListOf(SpecialSet(10.0, "strange")))
        val want = mutableListOf(
            SpecialSet(10.0, "strange"),
            NormalSet(20.0, 10),
            SpecialSet(27.5, "eh", 8),
            DropSet(30.0)
        )

        exercise.sets.addAll(
            listOf(
                sut.createWorkoutSet("10x 20"),
                sut.createWorkoutSet("8x 27.5 (eh)"),
                sut.createWorkoutSet("Dropsatz von 30")
            )
        )
        val have = exercise.sets

        assertEquals(want, have)
    }


    @Test
    fun addSetEmpty() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createWorkoutSet("")
        }
    }

    @Test
    fun addSetInvalid1() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createWorkoutSet("10x .25")
        }
    }

    @Test
    fun addSetInvalid2() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createWorkoutSet("10x (info)")
        }
    }

    @Test
    fun addSetInvalid3() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createWorkoutSet(".5")
        }
    }

    @Test
    fun addSetInvalid4() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createWorkoutSet("10x")
        }
    }

    @Test
    fun addSetInvalid5() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createWorkoutSet("10x 10 )")
        }
    }

    @Test
    fun addSetInvalid6() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createWorkoutSet("10x 10 ()")
        }
    }

    @Test
    fun addSetInvalid7() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createWorkoutSet("10x 10 )")
        }
    }

    @Test
    fun addSetInvalid8() {
        assertThrows(IllegalArgumentException::class.java) {
            sut.createWorkoutSet("5 a")
        }
    }

    @Test
    fun exerciseGetStringFull() {
        val machine = Machine("MTS Crunch", "Höhe 8")
        val exercise = Exercise(machine)
        exercise.sets.addAll(
            listOf(
                sut.createWorkoutSet("10 (strange)"),
                sut.createWorkoutSet("10x 20"),
                sut.createWorkoutSet("27.5 (eh)"),
                sut.createWorkoutSet("Dropsatz von 30")
            )
        )

        val want = "MTS Crunch (Höhe 8): 10 (strange), 10x 20, 27.5 (eh), Dropsatz von 30"

        val have = exercise.toString()

        assertEquals(want, have)
    }
}