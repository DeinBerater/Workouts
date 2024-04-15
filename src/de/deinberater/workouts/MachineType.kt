package de.deinberater.workouts

enum class MachineType {

    /** A normal machine which weight starts minimally */
    DefaultMachine,

    /** A machine whose weights are plates */
    PlateMachine,

    /** A machine which uses your own body weight and sometimes extra weight */
    BodyWeightMachine,

    /** Normal dumbbells, generally with less weight than machines */
    Dumbbells,

    /** An exercise with a bar */
    Bar,

    /** A machine with an unknown type */
    Unknown
}