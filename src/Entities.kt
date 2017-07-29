import javax.print.attribute.IntegerSyntax

typealias Coord = Double

open class Anchor(var point: Vector, var radius: Double) {

    constructor(x: Coord, y: Coord, radius: Double) : this(Vector(x, y), radius)
    constructor(x: Int, y: Int, radius: Int) : this(x.toDouble(), y.toDouble(), radius.toDouble())

}

class FlowPoint(point: Vector, radius: Double, var direction: Direction) : Anchor(point, radius) {

    constructor(x: Coord, y: Coord, radius: Double, direction: Double) : this(Vector(x, y), radius, Direction.fromDegrees(direction))
    constructor(x: Int, y: Int, radius: Int, direction: Int) : this(x.toDouble(), y.toDouble(), radius.toDouble(), direction.toDouble())

}

data class FlowLine(val pa: Anchor, val pb: Anchor) {
    fun nearestPoint(point: Vector): Vector {
        val aToP = pa.point.vectorTo(point)
        val aToB = pa.point.vectorTo(pb.point)
        val atb2 = Math.pow(aToB.x, 2.0) + Math.pow(aToB.y, 2.0)
        val atp_dot_atb = aToP.x * aToB.x + aToP.y * aToB.y

        val t = Utils.toRange(atp_dot_atb / atb2, 0.0, 1.0)

        return pa.point.plus(aToB.multiply(t))
    }

    fun nearestFlowPoint(point: Vector): FlowPoint {
        val aToP = pa.point.vectorTo(point)
        val aToB = pa.point.vectorTo(pb.point)
        val atb2 = Math.pow(aToB.x, 2.0) + Math.pow(aToB.y, 2.0)
        val atp_dot_atb = aToP.x * aToB.x + aToP.y * aToB.y

        val t = Utils.toRange(atp_dot_atb / atb2, 0.0, 1.0)

        return FlowPoint(pa.point.plus(aToB.multiply(t)), t * pb.radius + (1 - t) * pa.radius, aToB.direction())
    }
}

data class Vector(val x: Coord = 0.0, val y: Coord = 0.0) {
    companion object {
        fun radial(direction: Direction, size: Double = 1.0) = Vector(
                Math.cos(direction.value) * size,
                Math.sin(direction.value) * size
        )
    }

    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())

    val xI: Int
        get() = x.toInt()
    val yI: Int
        get() = y.toInt()

    fun size() = Math.sqrt(x * x + y * y)
    fun vectorTo(point: Vector) = Vector(point.x - x, point.y - y)
    fun distanceTo(point: Vector) = vectorTo(point).size()
    fun direction() = Direction(Math.atan2(y, x))
    fun directionTo(point: Vector) = vectorTo(point).direction()
    fun plus(vector: Vector) = Vector(x + vector.x, y + vector.y)
    fun minus(vector: Vector) = Vector(x - vector.x, y - vector.y)
    fun multiply(k: Double) = Vector(x * k, y * k)
    fun invert() = Vector(-x, -y)
}

class Direction(value: Double) {
    companion object {
        fun normalize(value: Double): Double {
            var res = value
            while (res < 0) res += 2 * Math.PI
            while (res >= 2 * Math.PI) res -= 2 * Math.PI
            return res;
        }

        fun fromDegrees(degrees: Double): Direction {
            return Direction(degrees * Math.PI / 180);
        }
    }

    val value: Double

    init {
        this.value = normalize(value)
    }

    fun difference(direction: Direction) = Math.min(
            normalize(this.value - direction.value),
            normalize(direction.value - this.value)
    )

    fun differenceTo(direction: Direction) = normalize(direction.value - this.value)
    fun plus(direction: Direction) = Direction(this.value + direction.value)
    fun minus(direction: Direction) = Direction(this.value - direction.value)
    fun invert() = Direction(this.value + Math.PI)
    fun degrees() = this.value * 180 / Math.PI
    fun degreesNice(): Int {
        var v = degrees()
        if (v > 180) v -= 360
        return Math.round(v).toInt()
    }

    fun mirrorWith(axis: Direction) = Direction(2 * axis.value - this.value)
    fun averageWith(direction: Direction) = this.weightedAverageWith(direction, 0.5)
    fun weightedAverageWith(direction: Direction, weight: Double)
            = Vector.radial(this, 1 - weight).plus(Vector.radial(direction, weight)).direction()
}

class Area(xa: Int, ya: Int, xb: Int, yb: Int) {
    var xa: Int
    var ya: Int
    var xb: Int
    var yb: Int

    val centerX
        get() = (xa + xb) / 2
    val centerY
        get() = (ya + yb) / 2
    val center
        get() = Vector(centerX, centerY)

    init {
        this.xa = minOf(xa, xb)
        this.ya = minOf(ya, yb)
        this.xb = maxOf(xa, xb)
        this.yb = maxOf(ya, yb)
    }
}

class Environment(var randomnessSize: Double) {
    var anchors: MutableList<Anchor> = mutableListOf()
    var flowLines: MutableList<FlowLine> = mutableListOf()
    var flowPoints: MutableList<FlowPoint> = mutableListOf()
    var flowRoutes: MutableList<FlowRoute> = mutableListOf()
}

class Route() {
    var points: MutableList<Vector> = mutableListOf()
}

class FlowRoute(val withEnd: Boolean) {
    var points: MutableList<Anchor> = mutableListOf()
}

data class Wall(
        var a: Vector,
        var b: Vector
) {
    fun distanceToPoint(point: Vector): Double {
        val aToP = a.vectorTo(point)
        val aToB = a.vectorTo(b)
        val atb2 = Math.pow(aToB.x, 2.0) + Math.pow(aToB.y, 2.0)
        val atp_dot_atb = aToP.x * aToB.x + aToP.y * aToB.y

        val tx = Utils.toRange(atp_dot_atb / atb2, 0.0, 1.0)

        return a.plus(aToB.multiply(tx)).distanceTo(point)
    }
}

data class SuperobjectRule(
        var area: Area,
        var entry: Area,
        var route: Route
)

class FlowPointAndLine(val flowPoint: FlowPoint?, val flowLine: FlowLine?) {
    operator fun component1() = flowPoint
    operator fun component2() = flowLine
}

object Utils {
    fun sign(value: Double) = if (value >= 0) 1 else -1
    fun toRange(value: Double, min: Double, max: Double) = Math.min(Math.max(min, value), max)
}

class KotlinTest
