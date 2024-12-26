package de.deinberater.workouts.machines

class Machines {
    companion object {
        private const val BODY_WEIGHT = 67.0

        fun getMoreInfoByName(machineName: String): Map<String, Any> {
            val machines = buildMap {
                put("Stepper", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("MTS Crunch", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Row", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Shrugs Stange", mapOf("type" to MachineType.Bar, "baseWeight" to 20.0))
                put("Pulldown", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Abdominal", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Back Extension", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Torso Rotation", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Shoulder Press", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Lateral Raise", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Triceps Press", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Biceps Curl", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Shrugs Trap Bar", mapOf("type" to MachineType.Bar, "baseWeight" to 20.0))
                put("Squats Stange", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to BODY_WEIGHT + 20.0))
                put("Deadlifts", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 20.0))
                put("MTS Shoulder Press", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Oblique Crunch", mapOf("type" to MachineType.PlateMachine, "baseWeight" to 0.0))
                put("Oblique Crunch Seitlich", mapOf("type" to MachineType.PlateMachine, "baseWeight" to 0.0))
                put("MTS Bizeps", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Standing Calf Raise", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to BODY_WEIGHT))
                put("MTS Rudern", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("MTS Latzug", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("MTS Beinstrecker", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Kneeling Leg Curl", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Iso-Lateral Kneeling Leg", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("MTS Armstrecker", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("MTS Bankdrücken", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Shrugs", mapOf("type" to MachineType.PlateMachine, "baseWeight" to 20.0))
                put("Shrugs Kabelzug", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Beinpresse", mapOf("type" to MachineType.PlateMachine, "baseWeight" to 50.0))
                put("Lat Pull", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Wadeln Beinpresse", mapOf("type" to MachineType.PlateMachine, "baseWeight" to 50.0))
                put("Rudern", mapOf("type" to MachineType.PlateMachine, "baseWeight" to 0.0))
                put("Rudern Hanteln", mapOf("type" to MachineType.Dumbbells, "baseWeight" to 0.0))
                put("Latzug", mapOf("type" to MachineType.PlateMachine, "baseWeight" to 0.0))
                put("Trizeps Seilzug", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Biceps Curl Seil", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Cable Flies", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Lateral Raise Kabelzug", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Squats", mapOf("type" to MachineType.PlateMachine, "baseWeight" to BODY_WEIGHT))
                put("Seated Calf", mapOf("type" to MachineType.PlateMachine, "baseWeight" to 0.0))
                put("Seated Dip", mapOf("type" to MachineType.PlateMachine, "baseWeight" to 0.0))
                put("Seated Bicep", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Iso-Lateral Incline Press", mapOf("type" to MachineType.PlateMachine, "baseWeight" to 0.0))
                put("Iso-Lateral Decline Press", mapOf("type" to MachineType.PlateMachine, "baseWeight" to 0.0))
                put("Iso-Lateral Bench Press", mapOf("type" to MachineType.PlateMachine, "baseWeight" to 0.0))
                put("Blaues Teil", mapOf("type" to MachineType.BodyWeightMachine, "baseWeight" to 0.0))
                put("Planks", mapOf("type" to MachineType.BodyWeightMachine, "baseWeight" to BODY_WEIGHT))
                put("Bizeps Hanteln", mapOf("type" to MachineType.Dumbbells, "baseWeight" to BODY_WEIGHT))
                put("Bankdrücken gerade", mapOf("type" to MachineType.Bar, "baseWeight" to 0.0))
                put("Bankdrücken schräg oben", mapOf("type" to MachineType.Bar, "baseWeight" to 0.0))
                put("Bankdrücken schräg unten", mapOf("type" to MachineType.Bar, "baseWeight" to 0.0))
                put("Sit-Ups schräg", mapOf("type" to MachineType.BodyWeightMachine, "baseWeight" to 0.0))
                put("Biceps Curl Stange", mapOf("type" to MachineType.Bar, "baseWeight" to 10.0))
                put("Shrugs Hanteln", mapOf("type" to MachineType.Dumbbells, "baseWeight" to 0.0))
                put("Trizeps über Kopf", mapOf("type" to MachineType.Dumbbells, "baseWeight" to 0.0))
                put("Skull Crusher", mapOf("type" to MachineType.Bar, "baseWeight" to 10.0))
                put("Reverse Flies", mapOf("type" to MachineType.Dumbbells, "baseWeight" to 0.0))
                put("Concentration Curls", mapOf("type" to MachineType.Dumbbells, "baseWeight" to 0.0))
                put("Hammer Curls", mapOf("type" to MachineType.Dumbbells, "baseWeight" to 0.0))
                put("Shoulder Hanteln", mapOf("type" to MachineType.Dumbbells, "baseWeight" to 0.0))
                put("Lateral Raise Hanteln", mapOf("type" to MachineType.Dumbbells, "baseWeight" to 0.0))
                put("Hanteln", mapOf("type" to MachineType.Dumbbells, "baseWeight" to 0.0))
                put("Bulgarian Split Squats", mapOf("type" to MachineType.Dumbbells, "baseWeight" to BODY_WEIGHT))
                put("Rear Deltoid", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Rear Deltoid Kabelzug", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Pectoral Fly", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Chest Press", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Hip Abduction", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Hip Adduction", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Glute", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Leg Curl", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Seated Leg Curl", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Seated Leg Press", mapOf("type" to MachineType.PlateMachine, "baseWeight" to 0.0))
                put("Leg Extension", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Chicken", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Lat Pulldown Seil", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Chest Press Seil", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Seated Row Seil", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Shoulder Press Seil", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
                put("Seated Row Seil", mapOf("type" to MachineType.DefaultMachine, "baseWeight" to 0.0))
            }

            // Get the first machine that matches the machineName (lowercase).
            // If not found, return a machine with no information.
            val machine = machines.filter { it.key.lowercase() == machineName.lowercase() }.values.firstOrNull()
            return machine ?: mapOf("type" to MachineType.Unknown, "baseWeight" to 0.0)
        }
    }
}