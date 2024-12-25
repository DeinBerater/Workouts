package de.deinberater.workouts.workoutsets

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class WorkoutSetTest {

    @Test
    fun copyWithNewWeight() {
        val sut = NormalSet(30.0)
        val have = sut.copyWithNewWeight(50.0)

        assertNotEquals(sut, have)
    }

    @Test
    fun copyWithNewWeight2() {
        val sut = NormalSet(30.0)
        val sutWant = NormalSet(30.0)
        val want = NormalSet(50.0)

        val have = sut.copyWithNewWeight(50.0)

        assertEquals(sutWant, sut)
        assertEquals(want, have)
    }

    @Test
    fun copyWithNewWeightSpecial() {
        val sut = SpecialSet(30.0, "wtf")
        val have = sut.copyWithNewWeight(50.0)

        val want = SpecialSet(50.0, "wtf")

        assertEquals(want, have)
    }

    @Test
    fun copyWithNewWeightSpecial2() {
        val sut = SpecialSet(30.0, "wtf")
        val have = sut.copyWithNewWeight(50.0)

        val want = SpecialSet(50.0, "wtf", 12)

        assertEquals(want, have)
    }

    @Test
    fun copyWithNewWeightDropSet() {
        val sut = DropSet(30.0)
        val have = sut.copyWithNewWeight(50.0)

        val want = DropSet(50.0)

        assertEquals(want, have)
    }

    @Test
    fun equals() {
        val sut = SpecialSet(30.0, "wtf", 11)
        val equal = SpecialSet(30.0, "wtf", 11)

        assertEquals(equal, sut)
    }

    @Test
    fun equals2() {
        val sut = NormalSet(30.3, 11)
        val equal = NormalSet(30.3, 11)

        assertEquals(equal, sut)
    }

    @Test
    fun equals3() {
        val sut = SpecialSet(30.0, "wtf")

        assertEquals(sut, sut)
    }

    @Test
    fun equals4() {
        val sut = NormalSet(30.0)
        val equal = NormalSet(30.0, 12)

        assertEquals(equal, sut)
    }

    @Test
    fun notEquals() {
        val sut = SpecialSet(30.0, "wtf")
        val other = DropSet(30.0)

        assertNotEquals(sut, other)
    }

    @Test
    fun notEquals2() {
        val sut = SpecialSet(30.0, "wtf")
        val other = SpecialSet(30.0, "other")

        assertNotEquals(sut, other)
    }

    @Test
    fun notEquals3() {
        val sut = SpecialSet(30.0, "wtf")
        val other = SpecialSet(30.1, "wtf")

        assertNotEquals(sut, other)
    }

    @Test
    fun notEquals4() {
        val sut = NormalSet(30.0)
        val other = NormalSet(30.1)

        assertNotEquals(sut, other)
    }

    @Test
    fun notEquals5() {
        val sut = NormalSet(30.0)
        val other = NormalSet(30.0, 11)

        assertNotEquals(sut, other)
    }

    @Test
    fun notEquals6() {
        val sut = NormalSet(30.3)
        val other = NormalSet(30.0, 11)

        assertNotEquals(sut, other)
    }

    @Test
    fun notEquals7() {
        val sut = NormalSet(30.0)
        val other = SpecialSet(30.0, "info")

        assertNotEquals(sut, other)
    }

    @Test
    fun notEquals8() {
        val sut = NormalSet(30.4)
        val other = SpecialSet(30.4, "info")

        assertNotEquals(sut, other)
    }

    @Test
    fun notEquals9() {
        val sut = DropSet(30.0)
        val other = NormalSet(30.0)

        assertNotEquals(sut, other)
    }

    @Test
    fun notEquals10() {
        val sut = NormalSet(30.0)
        val other = DropSet(30.0)

        assertNotEquals(sut, other)
    }

    @Test
    fun getBaseWeight() {
        val sut = NormalSet(10.0)
        val want = 10.0

        val have = sut.baseWeight

        assertEquals(want, have)
    }

    @Test
    fun toStringTest() {
        val sut = NormalSet(10.0)
        val want = "10"

        val have = sut.toString()

        assertEquals(want, have)
    }

    @Test
    fun toStringWithDecimals() {
        val sut = NormalSet(12.5)
        val want = "12.5"

        val have = sut.toString()

        assertEquals(want, have)
    }

    @Test
    fun toStringWithReps() {
        val sut = NormalSet(12.5, 10)
        val want = "10x 12.5"

        val have = sut.toString()

        assertEquals(want, have)
    }

    @Test
    fun toStringWith12Reps() {
        val sut = NormalSet(12.5, 12)
        val want = "12.5"

        val have = sut.toString()

        assertEquals(want, have)
    }

    @Test
    fun toStringWith12RepsSpecial() {
        val sut = SpecialSet(12.5, "wtf", 12)
        val want = "12.5 (wtf)"

        val have = sut.toString()

        assertEquals(want, have)
    }

    @Test
    fun toStringSpecial() {
        val sut = SpecialSet(12.5, "wtf")
        val want = "12.5 (wtf)"

        val have = sut.toString()

        assertEquals(want, have)
    }

    @Test
    fun toStringWithRepsSpecial() {
        val sut = SpecialSet(12.5, "wtf", 10)
        val want = "10x 12.5 (wtf)"

        val have = sut.toString()

        assertEquals(want, have)
    }

    @Test
    fun toStringDropSet() {
        val sut = DropSet(10.0)
        val want = "Dropsatz von 10"

        val have = sut.toString()

        assertEquals(want, have)
    }

    @Test
    fun toStringDropSetDecimal() {
        val sut = DropSet(11.25)
        val want = "Dropsatz von 11.25"

        val have = sut.toString()

        assertEquals(want, have)
    }


    @Test
    fun repAmount() {
        val sut = NormalSet(12.5, 10)
        val want = 10
        val have = sut.reps()

        assertEquals(want, have)
    }

    @Test
    fun repAmountDefault() {
        val sut = NormalSet(12.5)
        val want = 12
        val have = sut.reps()

        assertEquals(want, have)
    }

    @Test
    fun repAmountSpecialSet() {
        val sut = SpecialSet(12.5, "wtf", 10)
        val want = 10
        val have = sut.reps()

        assertEquals(want, have)
    }
}