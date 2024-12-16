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
}