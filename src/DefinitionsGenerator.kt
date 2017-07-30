import java.io.FileOutputStream
import java.io.PrintWriter

fun generateDefinitions(defs: DefinitionHolder) = defs.run {
    PrintWriter(FileOutputStream("src/Definitions.kt")).use { pw ->
        pw.println("""fun Flows._init_values() {
    // =====DEFINITIONS=====
""")
        walls.forEach { pw.println("    _wall(${it.a.xI}, ${it.a.yI}, ${it.b.xI}, ${it.b.yI});") }
        pw.println()

        areaGroups.forEach { area ->
            pw.println("    _area_group();")
            area.areas.forEach { pw.println("    _area(${it.xa}, ${it.ya}, ${it.xb}, ${it.yb});") }
            pw.println()
        }
        pw.println()

        environments.forEach { env ->
            if (env.randomnessSize == Flows.STD_RANDOMNESS) {
                pw.println("    _environment(STD_RANDOMNESS);")
            } else {
                pw.println("    _environment(${env.randomnessSize});")
            }
            pw.println()

            env.flowRoutes.forEach { route ->
                pw.println("    _environment_route(${route.withEnd});")
                route.points.forEach { p ->
                    pw.println("    _environment_route_point(${p.point.xI}, ${p.point.yI}, ${p.radius.toInt()});")
                }
                pw.println()
            }

            env.flowPoints.forEach { p ->
                pw.println("    _flowPoint(${p.point.xI}, ${p.point.yI}, ${p.radius.toInt()}, ${p.direction.degreesNice()});")
            }
            pw.println()

            pw.println()
        }
        pw.println("}")
    }

}