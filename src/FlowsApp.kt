import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.stage.Stage

class FlowsApp : Application() {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            Application.launch(FlowsApp::class.java, *args)
        }

        const val CANVAS_WIDTH = 600.0
        const val CANVAS_HEIGHT = 500.0
        const val CANVAS_STEP = 12.0

    }

    val flowPoints = mutableListOf(
            AnchorPoint(100, 80, 75),
            AnchorPoint(77, 220, 75),
            AnchorPoint(220, 360, 75),
            AnchorPoint(410, 370, 150),
            AnchorPoint(430, 350, 150),
            AnchorPoint(420, 170, 30)
    )
    val flowLines = flowPoints.mapIndexed { i, flowPoint -> FlowLine(flowPoint, flowPoints[(i + 1) % flowPoints.size]) }

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

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Flows"
        val root = Group()
        val canvas = Canvas(CANVAS_WIDTH, CANVAS_HEIGHT)
        val gc = canvas.graphicsContext2D
        drawShapes(gc)
        root.children.add(canvas)
        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

    private fun drawShapes(gc: GraphicsContext) {
        gc.lineWidth = 2.0
        gc.stroke = Color.BLACK
        gc.strokeRect(0.0, 0.0, CANVAS_WIDTH, CANVAS_HEIGHT)

        // Draw markers
        var x = CANVAS_STEP / 2
        while (x < CANVAS_WIDTH) {
            var y = CANVAS_STEP / 2
            while (y < CANVAS_HEIGHT) {
                val position = Vector(x, y)

                var finalVector = Vector();

                val nearestFlowPoint = calculateNearestFlowPoint(position)
                val toNearestFlowPoint = position.directionTo(nearestFlowPoint.point)
                //val force = Math.abs(/*Math.cos(toFlowPoint.difference(flowPoint.direction))*/ 1) / Math.pow(flowPoint.point.distanceTo(position) / 100, 3.0);

                var flowDirection = toNearestFlowPoint
                if (toNearestFlowPoint.difference(position.directionTo(nearestFlowPoint.point)) > Math.PI / 2) {
                    // we are in front of the arrow, need to mirror the pull direction
                    flowDirection = flowDirection.invert().mirrorWith(nearestFlowPoint.direction)
                }

                val d = nearestFlowPoint.point.distanceTo(position) / nearestFlowPoint.radius
                val relativeAngle = nearestFlowPoint.direction.invert().difference(toNearestFlowPoint)
                var weight = calculateWeight(d)
                val NULL_ANGLE = 180.0
                weight *= Math.pow(Math.cos(relativeAngle / (NULL_ANGLE / 90)), 1.0 / 2)
                //val weight = 0.0
                val pullDirection = nearestFlowPoint.direction.weightedAverageWith(toNearestFlowPoint, weight)

                finalVector = finalVector.plus(Vector.radial(pullDirection, 1.0));

                val MARK_SIZE = 6.0
                val drawVector = Vector.radial(finalVector.direction(), MARK_SIZE).invert();

                gc.lineWidth = 1.0
                gc.stroke = Color.BLACK
                val RADIUS = 1.0
                gc.strokeOval(position.x, position.y, RADIUS, RADIUS);

                gc.stroke = Color.GREY
                gc.beginPath();
                gc.moveTo(position.x, position.y);
                gc.lineTo(position.x + drawVector.x, position.y + drawVector.y);
                gc.stroke();

                y += CANVAS_STEP
            }
            x += CANVAS_STEP
        }


        var i = 0;
        while (i < flowPoints.size) {
            val a = flowPoints[i]
            val b = flowPoints[(i + 1) % flowPoints.size]
            val SIZE = a.radius

            gc.stroke = Color.RED;
            gc.lineWidth = 2.0;
            gc.strokeOval(a.point.x - SIZE / 2, a.point.y - SIZE / 2, SIZE, SIZE)

            gc.lineWidth = 1.0
            gc.strokeLine(a.point.x, a.point.y, b.point.x, b.point.y)

            i++
        }


    }

    fun calculateWeight(ratio: Double) = 1 - Math.pow(2.0, -ratio * ratio)
}
