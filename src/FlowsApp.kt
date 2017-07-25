import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.shape.ArcType
import javafx.stage.Stage

class FlowsApp : Application() {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            Application.launch(FlowsApp::class.java, *args)
        }

        const val CANVAS_WIDTH = 600.0
        const val CANVAS_HEIGHT = 400.0
        const val CANVAS_STEP = 12.0

    }

    val flowPoints = mutableListOf<FlowPoint>(
            FlowPoint(100, 80, 60, 75),
            FlowPoint(120, 160, 30, 75)
    )

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
                for (flowPoint in flowPoints) {

                    val toFlowPoint = position.directionTo(flowPoint.point);
                    val force = Math.abs(/*Math.cos(toFlowPoint.difference(flowPoint.direction))*/ 1) / Math.pow(flowPoint.point.distanceTo(position) / 100, 3.0);

                    var flowDirection = toFlowPoint
                    if (flowPoint.direction.difference(position.directionTo(flowPoint.point)) > Math.PI / 2) {
                        // we are in front of the arrow, need to mirror the pull direction
                        flowDirection = flowDirection.invert().mirrorWith(flowPoint.direction)
                    }

                    val d = flowPoint.point.distanceTo(position) / flowPoint.radius
                    val relativeAngle = flowPoint.direction.invert().difference(toFlowPoint)
                    var weight = calculateWeight(d)
                    val NULL_ANGLE = 160.0
                    weight *= Math.pow(Math.cos(relativeAngle / (NULL_ANGLE/90)), 1.0/2)
                    //val weight = 0.0
                    val pullDirection = flowPoint.direction.weightedAverageWith(toFlowPoint, weight)

                    finalVector = finalVector.plus(Vector.radial(pullDirection, force));
                }

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

        // Draw flow points
        for (flowPoint in flowPoints) {
            val SIZE = 15.0;
            val offsetter = Vector.radial(flowPoint.direction, SIZE);

            gc.stroke = Color.RED;
            gc.lineWidth = 2.0;
            gc.beginPath();
            val bottom = flowPoint.point.minus(offsetter);
            gc.moveTo(bottom.x, bottom.y);
            val top = flowPoint.point.plus(offsetter);
            gc.lineTo(top.x, top.y);
            gc.stroke();

            gc.beginPath();
            val left = top.minus(Vector.radial(flowPoint.direction.plus(Direction.fromDegrees(30.0)), SIZE));
            val right = top.minus(Vector.radial(flowPoint.direction.minus(Direction.fromDegrees(30.0)), SIZE));
            gc.moveTo(left.x, left.y);
            gc.lineTo(top.x, top.y);
            gc.lineTo(right.x, right.y);
            gc.stroke();
        }


    }

    fun calculateWeight(ratio: Double) = 1-Math.pow(2.0, -ratio * ratio)
}
