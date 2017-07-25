typealias Coord = Double

data class FlowPoint(val point: Vector, val direction: Direction, val radius: Double) {

    constructor(x: Coord, y: Coord, degrees: Double, radius: Double) : this(Vector(x, y), Direction.fromDegrees(degrees), radius)
    constructor(x: Int, y: Int, degrees: Int, radius: Int) : this(x.toDouble(), y.toDouble(), degrees.toDouble(), radius.toDouble())

    fun distanceFromLine(point: Vector): Double {
        val directional = Vector.radial(this.direction);
        val a = directional.y;
        val b = -directional.x;
        val c = -a * this.point.x - b * this.point.y;
        return Math.abs(a * point.x + b * point.y + c) / Math.sqrt(a * a + b * b);
    }
}

data class Vector(val x: Coord = 0.0, val y: Coord = 0.0) {
    companion object {
        fun radial(direction: Direction, size: Double = 1.0) = Vector(
                Math.cos(direction.value) * size,
                Math.sin(direction.value) * size
        )
    }

    fun size() = Math.sqrt(x * x + y * y)
    fun vectorTo(point: Vector) = Vector(point.x - x, point.y - y)
    fun distanceTo(point: Vector) = vectorTo(point).size()
    fun direction() = Direction(Math.atan2(y, x))
    fun directionTo(point: Vector) = vectorTo(point).direction()
    fun plus(vector: Vector) = Vector(x + vector.x, y + vector.y)
    fun minus(vector: Vector) = Vector(x - vector.x, y - vector.y)
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
    fun mirrorWith(axis: Direction) = Direction(2 * axis.value - this.value)
    fun averageWith(direction: Direction) = this.weightedAverageWith(direction, 0.5)
    fun weightedAverageWith(direction: Direction, weight: Double)
            = Vector.radial(this, 1 - weight).plus(Vector.radial(direction, weight)).direction()
}

object Utils {
    fun sign(value: Double) = if (value >= 0) 1 else -1
}

class KotlinTest
