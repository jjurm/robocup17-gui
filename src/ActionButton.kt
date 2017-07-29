class ActionButton(val text: String, val action: Action) {
    //constructor(text: String, lambda: (env: Environment, point: Vector) -> Action?) : this(text, action(lambda))
}

fun afterSelectFlowPoint(description: String, handler: (FlowPoint, Vector) -> Unit) = action("select FlowPoint") { env, pos ->
    val flowPoint = env.flowPoints.minBy { pos.distanceTo(it.point) } ?: return@action null
    actionFinal(description) { _, point -> handler.invoke(flowPoint, point) }
}

fun afterSelectFlowRouteAnchor(description: String, handler: (Anchor, Environment, Vector) -> Unit)
        = action("select FlowRoute.Anchor") { env, lookupPoint ->
    val selectedAnchor = env.flowRoutes.map { route ->
        route.points.minBy { lookupPoint.distanceTo(it.point) }?.let { ValueTag(it, lookupPoint.distanceTo(it.point)) }
    }.filterNotNull().minBy { it.value }?.obj
    if (selectedAnchor == null) null
    else actionFinal(description) { _, point -> handler.invoke(selectedAnchor, env, point) }
}


fun createActionButtons(defs: DefinitionHolder): List<ActionButton> {
    val list: MutableList<ActionButton> = mutableListOf()

    list.add(ActionButton("Area -> new", action("select first point") { _, p1 ->
        actionFinal("select second point") { _, p2 ->
            defs.areas.add(Area(p1.xI, p1.yI, p2.xI, p2.yI))
        }
    }))
    list.add(ActionButton("Area -> delete", actionFinal("select Area") { _, p ->
        val area = defs.areas.minBy { it.distanceToBorder(p) }
        if (area != null) defs.areas.remove(area)
    }))

    list.add(ActionButton("Wall -> add", action("select first point") { _, pa ->
        actionFinal("select second point") { _, pb ->
            Flows.defs.walls.add(Wall(pa, pb))
        }
    }))
    list.add(ActionButton("Wall -> remove", actionFinal("select wall") { _, p ->
        defs.walls.minBy {
            it.distanceToPoint(p)
        }.let {
            defs.walls.remove(it)
        }
    }))

    list.add(ActionButton("FlowPoint -> new", action("select point") { _, pCoords ->
        actionFinal("select direction and radius") { env, target ->
            env.flowPoints.add(FlowPoint(pCoords, pCoords.distanceTo(target) * 2, pCoords.directionTo(target)))
        }
    }))
    list.add(ActionButton("FlowPoint -> move", afterSelectFlowPoint("select position") { flowPoint, dst ->
        flowPoint.point = dst
    }))
    list.add(ActionButton("FlowPoint -> edit radius", afterSelectFlowPoint("select radius") { flowPoint, radiusPoint ->
        flowPoint.radius = flowPoint.point.distanceTo(radiusPoint) * 2
    }))
    list.add(ActionButton("FlowPoint -> edit direction", afterSelectFlowPoint("select direction") { flowPoint, directionPoint ->
        flowPoint.direction = flowPoint.point.directionTo(directionPoint)
    }))
    list.add(ActionButton("FlowPoint -> delete", actionFinal("select FlowPoint") { env, point ->
        env.flowPoints.minBy { point.distanceTo(it.point) }.let { env.flowPoints.remove(it) }
    }))


    list.add(ActionButton("FlowRoute.Anchor -> new at end", action("select FlowRoute") { env, lookupPoint ->
        val selectedRoute = env.flowRoutes.map { route ->
            route.points.minBy { lookupPoint.distanceTo(it.point) }?.let { ValueTag(route, lookupPoint.distanceTo(it.point)) }
        }.filterNotNull().minBy { it.value }?.obj
        if (selectedRoute == null) null
        else action("select point") { _, dst ->
            actionFinal("select radius") { _, radiusPoint ->
                selectedRoute.points.add(Anchor(dst, dst.distanceTo(radiusPoint) * 2))
            }
        }
    }))
    list.add(ActionButton("FlowRoute.Anchor -> new before", action("select FlowRoute.Anchor") { env, lookupPoint ->
        val selectedPair = env.flowRoutes.map { route ->
            route.points.minBy { lookupPoint.distanceTo(it.point) }?.let { DoubleValueTag(route.points, it, lookupPoint.distanceTo(it.point)) }
        }.filterNotNull().minBy { it.value }
        if (selectedPair == null) null
        else action("select position") { _, pos ->
            actionFinal("select radius") { _, radiusPoint ->
                val index = selectedPair.list.indexOf(selectedPair.obj);
                selectedPair.list.add(index, Anchor(pos, pos.distanceTo(radiusPoint) * 2))
            }
        }
    }))
    list.add(ActionButton("FlowRoute.Anchor -> move", afterSelectFlowRouteAnchor("select position") { anchor, _, pos ->
        anchor.point = pos
    }))
    list.add(ActionButton("FlowRoute.Anchor -> edit radius", afterSelectFlowRouteAnchor("select radius") { anchor, _, pos ->
        anchor.radius = anchor.point.distanceTo(pos) * 2
    }))
    list.add(ActionButton("FlowRoute.Anchor -> delete", actionFinal("select FlowRoute.Anchor") { env, lookupPoint ->
        val selectedPair = env.flowRoutes.map { route ->
            route.points.minBy { lookupPoint.distanceTo(it.point) }?.let { DoubleValueTag(route.points, it, lookupPoint.distanceTo(it.point)) }
        }.filterNotNull().minBy { it.value }
        if (selectedPair != null) {
            selectedPair.list.remove(selectedPair.obj)
        }
    }))

    return list
}

fun Vector.distanceToLine(a: Vector, b: Vector): Double {
    val dst = a.distanceTo(b)
    if (dst == 0.0) return 0.0
    var t = ((x - a.x) * (b.x - a.x) + (y - a.y) * (b.y - a.y)) / dst;
    t = Math.max(0.0, Math.min(1.0, t));
    return distanceTo(Vector(x + t * (b.x - a.x), y + t * (b.y - a.y)))
}

fun Area.distanceToBorder(pos: Vector): Double {
    return listOf(
            pos.distanceToLine(Vector(xa, ya), Vector(xb, ya)),
            pos.distanceToLine(Vector(xb, ya), Vector(xb, yb)),
            pos.distanceToLine(Vector(xb, yb), Vector(xa, yb)),
            pos.distanceToLine(Vector(xa, yb), Vector(xa, ya))
    ).min()!!
}
