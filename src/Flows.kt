object Flows {

    const val DRAW_ENVIRONMENT = 0
    const val STD_RANDOMNESS = 0.1

    const val MAP_WIDTH = 360.0
    const val MAP_HEIGHT = 270.0
    const val BORDER_DISTANCE = 12.0
    const val BORDER_COEFF_K = 6.0
    const val DISCRETE_FLOWPOINT_FORCE = 2.0
    const val E_A = 0

    val defs = DefinitionHolder()

    init {
        _init_values()
        for (env in defs.environments) {
            env.flowLines = env.anchors.mapIndexed { i, flowPoint -> FlowLine(flowPoint, env.anchors[(i + 1) % env.anchors.size]) }.toMutableList()
        }
    }

    fun _environment(randomnessSize: Double) { // _environment must be defined before _anchor, _flowPoint
        defs.environments.add(Environment(randomnessSize))
    }

    fun _anchor(x: Int, y: Int, radius: Int) {
        defs.environments.last().anchors.add(Anchor(x, y, radius));
    }

    fun _flowPoint(x: Int, y: Int, radius: Int, direction: Int) {
        defs.environments.last().flowPoints.add(FlowPoint(x, y, radius, direction))
    }

    fun _rule(aXa: Int, aYa: Int, aXb: Int, aYb: Int, eXa: Int, eYa: Int, eXb: Int, eYb: Int) {
        defs.superobjectRules.add(SuperobjectRule(
                Area(aXa, aYa, aXb, aYb),
                Area(eXa, eYa, eXb, eYb),
                Route()
        ));
    }

    fun _rule_route_point(x: Int, y: Int) {
        defs.superobjectRules.last().route.points.add(Vector(x, y))
    }

    fun _wall(ax: Int, ay: Int, bx: Int, by: Int) {
        defs.walls.add(Wall(Vector(ax, ay), Vector(bx, by)))
    };

    fun _environment_route(withEnd: Boolean) {
        defs.environments.last().flowRoutes.add(FlowRoute(withEnd))
    }

    fun _environment_route_point(x: Int, y: Int, radius: Int) {
        defs.environments.last().flowRoutes.last().points.add(Anchor(x, y, radius))
    }

    fun _area(x1: Int, y1: Int, x2: Int, y2: Int) {
        defs.areas.add(Area(x1, y1, x2, y2));
    }

    fun calculateNearestFlowPoint(env: Environment, point: Vector): FlowPoint? {
        var distance: Double = Double.MAX_VALUE
        var nearest: FlowPoint? = null
        for (flowLine in env.flowLines) {
            val np = flowLine.nearestFlowPoint(point)
            val dst = point.distanceTo(np.point)
            if (distance > point.distanceTo(np.point)) {
                distance = dst
                nearest = np
            }
        }
        return nearest
    }

    fun calculateNearestFlowRoutePoint(flowRoute: FlowRoute, point: Vector): FlowPointAndLine {
        var distance: Double = Double.MAX_VALUE
        var nearest: FlowPoint? = null
        var line: FlowLine? = null
        var i = 0
        while (i < flowRoute.points.size - 1) {
            val flowLine = FlowLine(flowRoute.points[i], flowRoute.points[i + 1])
            val np = flowLine.nearestFlowPoint(point)
            val dst = point.distanceTo(np.point)
            if (distance > point.distanceTo(np.point)) {
                distance = dst
                nearest = np
                line = flowLine
            }
            i++
        }
        return FlowPointAndLine(nearest, line)
    }

    fun influenceByFlowPoint(position: Vector, flowPoint: FlowPoint?): Vector {
        if (flowPoint == null) return Vector();
        val toFlowPoint = position.directionTo(flowPoint.point)
        //val force = Math.abs(/*Math.cos(toFlowPoint.difference(flowPoint.direction))*/ 1) / Math.pow(flowPoint.point.distanceTo(position) / 100, 3.0);

        /*var flowDirection = toFlowPoint
        val angleDifference = toFlowPoint.difference(position.directionTo(flowPoint.point))

        if (angleDifference > Math.PI / 2) {
            // we are in front of the arrow, need to mirror the pull direction
            flowDirection = flowDirection.invert().mirrorWith(flowPoint.direction)
        }*/

        val distance = flowPoint.point.distanceTo(position)
        val d = distance / flowPoint.radius
        val relativeAngle = flowPoint.direction.invert().difference(toFlowPoint)
        var weight = 1 - Math.pow(2.0, -d * d)
        //weight *= Math.pow(Math.cos(relativeAngle / 2), 1.0 / 2)
        weight *= (-relativeAngle) / Math.PI + 1
        val pullDirection = flowPoint.direction.weightedAverageWith(toFlowPoint, weight)

        val size = 1 / (distance / 10 + 1)
        return Vector.radial(pullDirection, size)
    }

    fun influenceByFlowPointWithEnd(position: Vector, flowPoint: FlowPoint, flowLine: FlowLine): Vector {
        val toFlowPoint = position.directionTo(flowPoint.point)

        val d = flowPoint.point.distanceTo(position) / flowPoint.radius
        val relativeAngle = flowPoint.direction.invert().difference(toFlowPoint)
        var weight = 1 - Math.pow(2.0, -d * d)
        weight *= Math.pow(Math.cos(relativeAngle / 2), 1.0 / 2)
        val pullDirection = flowPoint.direction.weightedAverageWith(toFlowPoint, weight)

        val projectionDistance = flowPoint.point.distanceTo(flowLine.pb.point)
        weight = Utils.toRange(projectionDistance / flowLine.pb.radius - 1, 0.0, 1.0);
        val final = position.directionTo(flowLine.pb.point).weightedAverageWith(pullDirection, weight)

        return Vector.radial(final, 1.0)
    }

    fun influenceByBorders(position: Vector): Vector {
        return Vector()
                .plus(Vector.radial(Direction(0.0), forceOfBorder(position.x))) // left border
                .plus(Vector.radial(Direction(Math.PI / 2), forceOfBorder(position.y))) // bottom border
                .plus(Vector.radial(Direction(Math.PI), forceOfBorder(MAP_WIDTH - position.x))) // right border
                .plus(Vector.radial(Direction(Math.PI * 3 / 2), forceOfBorder(MAP_HEIGHT - position.y))) // top border
    }

    fun influenceByDiscreteFlowPoints(env: Environment, position: Vector): Vector {
        var sum = Vector();
        for (flowPoint in env.flowPoints) {
            sum = sum.plus(influenceByDiscreteFlowPoint(position, flowPoint));
        }
        return sum;
    }

    fun influenceByDiscreteFlowPoint(position: Vector, flowPoint: FlowPoint): Vector {
        return Vector.radial(flowPoint.direction, DISCRETE_FLOWPOINT_FORCE * forceOfFlowPoint(flowPoint, position.distanceTo(flowPoint.point)))
    }

    fun influenceByRoute(position: Vector, route: FlowRoute): Vector {
        val (nearest, line) = calculateNearestFlowRoutePoint(route, position)
        if (nearest == null || line == null) return Vector()
        if (route.withEnd && line.pb == route.points.last()) {
            return influenceByFlowPointWithEnd(position, nearest, line)
        } else {
            return influenceByFlowPoint(position, nearest)
        }
    }

    fun influenceByRoutes(env: Environment, position: Vector): Vector {
        var sum = Vector()
        for (flowRoute in env.flowRoutes) {
            sum = sum.plus(influenceByRoute(position, flowRoute));
        }
        return sum
    }

    fun calculateMoveVector(env: Environment, position: Vector): Vector {
        val nearestFlowPoint = calculateNearestFlowPoint(env, position);
        val infFlowPoint = influenceByFlowPoint(position, nearestFlowPoint);
        //val infFlowPoint = influenceByFlowPointForSuperobject(position, nearestFlowPoint, flowLines[0]);
        val infBorders = influenceByBorders(position);
        val infDiscretes = influenceByDiscreteFlowPoints(env, position);
        val infRoutes = influenceByRoutes(env, position)
        return infFlowPoint.plus(infBorders).plus(infDiscretes).plus(infRoutes)
    }

    fun forceOfBorder(distance: Double): Double {
        val a = BORDER_DISTANCE
        val b = BORDER_COEFF_K
        val x = distance
        return Math.max(0.0, 2 * (Math.exp((-x) / b) - Math.exp((-a) / b)) / (1 - Math.exp((-a) / b)))
    }

    fun forceOfFlowPoint(flowPoint: FlowPoint, distance: Double): Double {
        val a = flowPoint.radius
        val b = BORDER_COEFF_K
        val x = distance
        return Math.max(0.0, 2 * (Math.exp((-x) / b) - Math.exp((-a) / b)) / (1 - Math.exp((-a) / b)))
    }

}