import de.deinberater.workouts.Exercise
import de.deinberater.workouts.Workout
import de.deinberater.workouts.WorkoutType
import de.deinberater.workouts.machines.Machine
import de.deinberater.workouts.workoutsets.DropSet
import de.deinberater.workouts.workoutsets.NormalSet
import de.deinberater.workouts.workoutsets.SpecialSet
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class WorkoutTest {
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
    fun getAmountExercises() {
        val sut = Workout(WorkoutType.Legs, LocalDate.of(2024, 2, 5))
        val want = 0

        val have = sut.getAmountExercises()

        assertEquals(want, have)
    }

    @Test
    fun getAmountExercises2() {
        val sut = Workout(WorkoutType.Legs, LocalDate.of(2024, 2, 5))
        val want = 1

        sut.exercises.add(Exercise(Machine("Some machine")))
        val have = sut.getAmountExercises()

        assertEquals(want, have)
    }

    @Test
    fun getAmountExercises3() {
        val sut = Workout(WorkoutType.Legs, LocalDate.of(2024, 2, 5))
        val wantExercises = 5
        val wantSets = 0

        val machine = Machine("Some machine")

        for (i in 1..5) {
            sut.exercises.add(Exercise(machine))
        }

        val haveExercises = sut.getAmountExercises()
        val haveSets = sut.getAmountSets()

        assertEquals(wantExercises, haveExercises)
        assertEquals(wantSets, haveSets)
    }


    @Test
    fun getAmountSets() {
        val sut = Workout(WorkoutType.Legs, LocalDate.of(2024, 2, 5))
        val want = 10

        val machine = Machine("Some machine")

        // Practically the same exercises (same machine and sets) multiple times
        val exercise = Exercise(machine, mutableListOf(NormalSet(10.0), NormalSet(20.0)))

        for (i in 1..5) {
            sut.exercises.add(exercise)
        }
        val have = sut.getAmountSets()

        assertEquals(want, have)
    }

    @Test
    fun getAmountSets2() {
        val sut = Workout(WorkoutType.Legs, LocalDate.of(2024, 2, 5))
        val want = 11

        val machine = Machine("Some machine")

        // Practically the same exercises (same machine and sets) multiple times
        val exercise = Exercise(machine, mutableListOf(NormalSet(10.0), NormalSet(20.0)))

        for (i in 1..5) {
            sut.exercises.add(exercise)
        }
        sut.exercises.add(Exercise(machine, mutableListOf(DropSet(40.2))))
        val have = sut.getAmountSets()

        assertEquals(want, have)
    }

    @Test
    fun getVolume() {
        val sut = Workout(WorkoutType.Legs, LocalDate.of(2024, 2, 5))

        val machine = Machine("Some machine")

        // Practically the same exercises (same machine and sets) multiple times
        // Volume of 30
        val exercise = Exercise(machine, mutableListOf(NormalSet(10.0), NormalSet(20.0)))

        // 5 * 30 = 150. Times 12 reps = 1800
        for (i in 1..5) {
            sut.exercises.add(exercise)
        }
        // Volume of 40.2, times 12 reps = 482.4
        sut.exercises.add(Exercise(machine, mutableListOf(NormalSet(40.2))))
        val have = sut.getTotalVolume()

        // 1800 + 482.4 = 2282.4
        val want = 2282.4

        assertEquals(want, have)
    }

    @Test
    fun getVolume2() {
        val sut = Workout(WorkoutType.Legs, LocalDate.of(2024, 2, 5))

        val machine = Machine("Some machine")

        // Practically the same exercises (same machine and sets) multiple times
        // Volume of 30 * 12 reps = 360
        val exercise = Exercise(machine, mutableListOf(NormalSet(10.0), NormalSet(20.0)))

        // 5 * 360 = 1800
        for (i in 1..5) {
            sut.exercises.add(exercise)
        }
        // Volume of 40.2 + Machine base weight of 20. So 60.2, times 5 reps = 301
        sut.exercises.add(Exercise(Machine("Deadlifts"), mutableListOf(SpecialSet(40.2, "abc", 5))))
        val have = sut.getTotalVolume()

        // 1800 + 301 = 2101
        val want = 2101.0

        assertEquals(want, have)
    }

    @Test
    fun getVolume3() {
        val sut = Workout(WorkoutType.Legs, LocalDate.of(2024, 2, 5))

        val machine = Machine("Some machine")

        // Practically the same exercises (same machine and sets) multiple times
        // Volume of 30 * 12 reps = 360
        val exercise = Exercise(machine, mutableListOf(NormalSet(10.0), NormalSet(20.0)))

        // 5 * 360 = 1800
        for (i in 1..5) {
            sut.exercises.add(exercise)
        }

        // Volume of 40.2 + Machine base weight of 20. So 60.2, times 12 reps = 722.4
        sut.exercises.add(Exercise(Machine("Deadlifts"), mutableListOf(NormalSet(40.2))))
        val have = sut.getTotalVolume()

        // 1800 + 722.4 = 2522.4
        val want = 2522.4

        assertEquals(want, have)
    }
}