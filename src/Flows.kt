object Flows {

    const val MAP_WIDTH = 350.0
    const val MAP_HEIGHT = 260.0
    const val BORDER_DISTANCE = 20.0
    const val BORDER_COEFF_K = 16.0
    const val DISCRETE_FLOWPOINT_FORCE = 2.0
    const val E_A = 0

    val anchors = mutableListOf<AnchorPoint>()
    val flowLines: MutableList<FlowLine>
    val flowPoints = mutableListOf<FlowPoint>()

    fun _anchor(env: Int, x: Int, y: Int, radius: Int) {
        anchors.add(AnchorPoint(x, y, radius));
    }

    fun _flowPoint(env: Int, x: Int, y: Int, radius: Int, direction: Int) {
        flowPoints.add(FlowPoint(x, y, radius, direction))
    }

    init {
        /*_anchor(E_A, 88, 58, 20);
        _anchor(E_A, 102, 145, 10);
        _anchor(E_A, 90, 200, 35);
        _anchor(E_A, 197, 244, 4);
        _anchor(E_A, 280, 242, 4);
        _anchor(E_A, 292, 134, 20);
        _anchor(E_A, 337, 90, 4);
        _anchor(E_A, 337, 24, 4);
        _anchor(E_A, 190, 26, 20);*/
        _anchor(E_A, 100, 100, 4);
        _anchor(E_A, 200, 140, 40);
        //_flowPoint(E_A, 10, 165, 60, 80);

        flowLines = anchors.mapIndexed { i, flowPoint -> FlowLine(flowPoint, anchors[(i + 1) % anchors.size]) }.toMutableList()
        flowLines.removeAt(flowLines.size-1);
    }

    fun calculateNearestFlowPoint(point: Vector): FlowPoint {
        var distance: Double = Double.MAX_VALUE
        var nearest: FlowPoint? = null
        for (flowLine in flowLines) {
            val np = flowLine.nearestFlowPoint(point)
            val dst = point.distanceTo(np.point)
            if (distance > point.distanceTo(np.point)) {
                distance = dst
                nearest = np
            }
        }
        return nearest!!
    }

    fun influenceByFlowPoint(position: Vector, flowPoint: FlowPoint): Vector {
        val toFlowPoint = position.directionTo(flowPoint.point)
        //val force = Math.abs(/*Math.cos(toFlowPoint.difference(flowPoint.direction))*/ 1) / Math.pow(flowPoint.point.distanceTo(position) / 100, 3.0);

        /*var flowDirection = toFlowPoint
        val angleDifference = toFlowPoint.difference(position.directionTo(flowPoint.point))

        if (angleDifference > Math.PI / 2) {
            // we are in front of the arrow, need to mirror the pull direction
            flowDirection = flowDirection.invert().mirrorWith(flowPoint.direction)
        }*/

        val d = flowPoint.point.distanceTo(position) / flowPoint.radius
        val relativeAngle = flowPoint.direction.invert().difference(toFlowPoint)
        var weight = 1 - Math.pow(2.0, -d * d)
        weight *= Math.pow(Math.cos(relativeAngle / 2), 1.0 / 2)
        //val weight = 0.0
        val pullDirection = flowPoint.direction.weightedAverageWith(toFlowPoint, weight)

        return Vector.radial(pullDirection, 1.0)
    }

    fun influenceByFlowPointForSuperobject(position: Vector, flowPoint: FlowPoint, flowLine: FlowLine): Vector {
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

    fun influenceByDiscreteFlowPoints(position: Vector): Vector {
        var sum = Vector();
        for (flowPoint in flowPoints) {
            sum = sum.plus(influenceByDiscreteFlowPoint(position, flowPoint));
        }
        return sum;
    }

    fun influenceByDiscreteFlowPoint(position: Vector, flowPoint: FlowPoint): Vector {
        return Vector.radial(flowPoint.direction, DISCRETE_FLOWPOINT_FORCE * forceOfFlowPoint(flowPoint, position.distanceTo(flowPoint.point)))
    }

    fun calculateMoveVector(position: Vector): Vector {
        val nearestFlowPoint = calculateNearestFlowPoint(position);
        val infFlowPoint = influenceByFlowPoint(position, nearestFlowPoint);
        //val infFlowPoint = influenceByFlowPointForSuperobject(position, nearestFlowPoint, flowLines[0]);
        val infBorders = influenceByBorders(position);
        val infDiscretes = influenceByDiscreteFlowPoints(position);
        return infFlowPoint.plus(infBorders).plus(infDiscretes)
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