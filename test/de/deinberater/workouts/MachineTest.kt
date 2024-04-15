package de.deinberater.workouts

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class MachineTest {

    @Test
    fun machineName() {
        val string = "Great machine name"
        val sut = Machine(string, null)

        val have = sut.name
        assertEquals(string, have)
    }

    @Test
    fun constructorNoDetails() {
        val string = "Great machine name"
        val sut = Machine(string)

        val have = sut.details
        assertNull(have)
    }

    @Test
    fun machineNoDetails() {
        val string = "Great machine name"
        val sut = Machine(string, null)

        val have = sut.details
        assertNull(have)
    }

    @Test
    fun machineDetails() {
        val string = "Great machine name"
        val want = "Great details"
        val sut = Machine(string, want)

        val have = sut.details
        assertEquals(want, have)
    }

    @Test
    fun sameIdentical() {
        val string = "Great machine name"
        val sut = Machine(string, null)

        val have = sut.same(sut)
        assertTrue(have)
    }

    @Test
    fun sameIdentical2() {
        val string = "Great machine name"
        val sut = Machine(string, "Great details")

        val have = sut.same(sut)
        assertTrue(have)
    }

    @Test
    fun sameDifferentDetails() {
        val string = "Great machine name"
        val sut = Machine(string, "Great details")
        val other = Machine(string, "Other details")

        val have = sut.same(other)
        assertTrue(have)
    }

    @Test
    fun sameSameDetails() {
        val string = "Great machine name"
        val sut = Machine(string, "Great details")
        val other = Machine(string, "Great details")

        val have = sut.same(other)
        assertTrue(have)
    }

    @Test
    fun notSameButSameDetails() {
        val string = "Great machine name"
        val sut = Machine(string, "Great details")
        val other = Machine("Bad machine", "Great details")

        val have = sut.same(other)
        assertFalse(have)
    }

    @Test
    fun notSame() {
        val string = "Great machine name"
        val sut = Machine(string, "Great details")
        val other = Machine("Bad machine", "Bad details")

        val have = sut.same(other)
        assertFalse(have)
    }

    @Test
    fun notSame2() {
        val string = "Great machine name"
        val sut = Machine(string, null)
        val other = Machine("Bad machine", "Bad details")

        val have = sut.same(other)
        assertFalse(have)
    }

    @Test
    fun notSame3() {
        val string = "Great machine name"
        val sut = Machine(string, null)
        val other = Machine("Bad machine", null)

        val have = sut.same(other)
        assertFalse(have)
    }

    @Test
    fun notSame4() {
        val string = "Great machine name"
        val sut = Machine(string, "Good details")
        val other = Machine("Bad machine", null)

        val have = sut.same(other)
        assertFalse(have)
    }

    @Test
    fun isEqualSameMachine() {
        val string = "Great machine name"
        val sut = Machine(string, null)

        val have = sut.isEqual(sut)
        assertTrue(have)
    }

    @Test
    fun isEqualSameMachine2() {
        val string = "Great machine name"
        val sut = Machine(string, "Some details")

        val have = sut.isEqual(sut)
        assertTrue(have)
    }

    @Test
    fun isEqualNull() {
        val string = "Great machine name"
        val sut = Machine(string, "Some details")

        val have = sut.isEqual(null)
        assertFalse(have)
    }

    @Test
    fun isEqualColon() {
        val sut = Machine("Machine 1", "Info 1")
        val other = Machine("Machine 1", "Info: 1")

        val have = sut.isEqual(other)
        assertTrue(have)
    }

    @Test
    fun isEqualColon2() {
        val sut = Machine("Machine 1", "Info 1")
        val other = Machine("machine 1", "info: 1")

        val have = sut.isEqual(other)
        assertTrue(have)
    }

    @Test
    fun notEqual() {
        val string = "Great machine name"
        val sut = Machine(string, "Great details")
        val other = Machine(string, "Bad details")

        val have = sut.isEqual(other)
        assertFalse(have)
    }

    @Test
    fun notEqualDetailsNull1() {
        val string = "Great machine name"
        val sut = Machine(string, null)
        val other = Machine(string, "Bad details")

        val have = sut.isEqual(other)
        assertFalse(have)
    }

    @Test
    fun notEqualDetailsNull2() {
        val string = "Great machine name"
        val sut = Machine(string, "Good details")
        val other = Machine(string, null)

        val have = sut.isEqual(other)
        assertFalse(have)
    }

    @Test
    fun notEqualAtAll() {
        val string = "Great machine name"
        val sut = Machine(string, "Good details")
        val other = Machine("Bad machine", "Wrong details")

        val have = sut.isEqual(other)
        assertFalse(have)
    }

    @Test
    fun equals1() {
        val string = "Great machine name"
        val sut = Machine(string, "Good details")
        val other = Machine(string, "Good details")

        assertEquals(sut, other)
    }

    @Test
    fun notEquals() {
        val string = "Great machine name"
        val sut = Machine(string, "Good details")
        val other = Machine(string, "Good: details")

        assertNotEquals(sut, other)
    }

    @Test
    fun equalsNull() {
        val string = "Great machine name"
        val sut = Machine(string, "Good details")

        assertNotEquals(sut, null)
    }


    @Test
    fun toStringTest() {
        val sut = Machine("My machine", "Good details")
        val want = "My machine (Good details)"

        val have = sut.toString()

        assertEquals(want, have)
    }

    @Test
    fun toStringNoDetails() {
        val sut = Machine("My machine", null)
        val want = "My machine"

        val have = sut.toString()

        assertEquals(want, have)
    }

    @Test
    fun toStringNoDetails2() {
        val sut = Machine("My machine")
        val want = "My machine"

        val have = sut.toString()

        assertEquals(want, have)
    }

    @Test
    fun machineTypeDumbbells() {
        val sut = Machine("Hammer Curls", null)
        val want = MachineType.Dumbbells

        val have = sut.type

        assertEquals(want, have)
    }

    @Test
    fun machineTypeDefault() {
        val sut = Machine("Pectoral Fly", "Enge 2")
        val want = MachineType.DefaultMachine

        val have = sut.type

        assertEquals(want, have)
    }

    @Test
    fun realWeight() {
        val sut = Machine("Deadlifts")
        val want = 20.0

        val have = sut.getRealWeight(0.0)

        assertEquals(want, have)
    }

    @Test
    fun realWeight2() {
        val sut = Machine("Deadlifts")
        val want = 23.75

        val have = sut.getRealWeight(3.75)

        assertEquals(want, have)
    }

    @Test
    fun realWeight3() {
        val sut = Machine("MTS Crunch")
        val want = 3.75

        val have = sut.getRealWeight(3.75)

        assertEquals(want, have)
    }
}