import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.TextArea
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.stage.Stage
import java.io.FileInputStream

class FlowsApp : Application() {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            Application.launch(FlowsApp::class.java, *args)
        }

        const val SCALE = 2.0;
        const val IMG_WIDTH = 350.0
        const val IMG_HEIGHT = 260.0
        const val CANVAS_WIDTH = IMG_WIDTH * SCALE;
        const val CANVAS_HEIGHT = IMG_HEIGHT * SCALE;
        const val CANVAS_STEP = 8.0
        const val BORDER_DISTANCE = 30.0
        const val BORDER_COEFF_K = 20.0
    }

    val flowPoints = mutableListOf<AnchorPoint>()
    val flowLines: List<FlowLine>

    fun _anchor(x: Int, y: Int, radius: Int) {
        flowPoints.add(AnchorPoint(x, y, radius));
    }

    init {
        _anchor(102, 44, 20);
        _anchor(93, 58, 20);
        _anchor(110, 190, 20);
        _anchor(155, 210, 20);
        _anchor(175, 195, 20);
        _anchor(205, 113, 20);
        _anchor(160, 40, 20);


        flowLines = flowPoints.mapIndexed { i, flowPoint -> FlowLine(flowPoint, flowPoints[(i + 1) % flowPoints.size]) }
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

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Flows"
        val root = Group()

        val text = TextArea()
        text.layoutY = CANVAS_HEIGHT
        text.maxHeight = 40.0

        val canvas = Canvas(CANVAS_WIDTH, CANVAS_HEIGHT)
        canvas.onMouseMoved = EventHandler<MouseEvent> { e ->
            var p = Vector(e.sceneX.toInt() / SCALE, e.sceneY.toInt() / SCALE);
            p = Vector(p.x, IMG_HEIGHT - p.y)
            text.text = String.format("%d, %d", p.x.toInt(), p.y.toInt())
        }
        val gc = canvas.graphicsContext2D
        drawShapes(gc)
        root.children.add(canvas)

        root.children.add(text)


        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

    private fun toCX(posX: Double): Double = posX * SCALE
    private fun toCY(posY: Double): Double = CANVAS_HEIGHT - posY * SCALE
    private fun toCR(r: Double) = r * SCALE

    private fun drawShapes(gc: GraphicsContext) {
        val image = javafx.scene.image.Image(FileInputStream("img.png"))

        gc.drawImage(image, 0.0, 0.0, IMG_WIDTH, IMG_HEIGHT, 0.0, 0.0, CANVAS_WIDTH, CANVAS_HEIGHT)

        gc.lineWidth = 2.0
        gc.stroke = Color.BLACK
        gc.strokeRect(0.0, 0.0, CANVAS_WIDTH, CANVAS_HEIGHT)

        // Draw markers
        var x = CANVAS_STEP / 2
        while (x < CANVAS_WIDTH) {
            var y = CANVAS_STEP / 2
            while (y < CANVAS_HEIGHT) {
                val position = Vector(x, y)

                val finalVector = calculateMoveVector(position)

                val MARK_SIZE = 2.0
                val drawVector = Vector.radial(finalVector.direction(), toCR(MARK_SIZE)).invert();

                gc.lineWidth = 1.0
                gc.stroke = Color.BLACK
                val RADIUS = 2.0
                gc.strokeOval(toCX(position.x) - toCR(RADIUS) / 2, toCY(position.y) - toCR(RADIUS) / 2, toCR(RADIUS), toCR(RADIUS));

                gc.stroke = Color.rgb(100, 255, 100);
                gc.beginPath();
                gc.moveTo(toCX(position.x), toCY(position.y));
                gc.lineTo(toCX(position.x + drawVector.x), toCY(position.y + drawVector.y));
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
            gc.strokeOval(toCX(a.point.x) - toCR(SIZE) / 2, toCY(a.point.y) - toCR(SIZE) / 2, toCR(SIZE), toCR(SIZE))

            gc.lineWidth = 1.0
            gc.strokeLine(toCX(a.point.x), toCY(a.point.y), toCX(b.point.x), toCY(b.point.y))

            i++
        }
    }

    private fun calculateMoveVector(position: Vector): Vector {
        val nearestFlowPoint = calculateNearestFlowPoint(position)
        val influenceByBorder = influenceByBorder(position)
        val influence = influenceByFlowPoint(position, nearestFlowPoint).plus(influenceByBorder)
        return influence
    }

    private fun influenceByFlowPoint(position: Vector, flowPoint: FlowPoint): Vector {
        val toFlowPoint = position.directionTo(flowPoint.point)
        //val force = Math.abs(/*Math.cos(toFlowPoint.difference(flowPoint.direction))*/ 1) / Math.pow(flowPoint.point.distanceTo(position) / 100, 3.0);

        var flowDirection = toFlowPoint
        val angleDifference = toFlowPoint.difference(position.directionTo(flowPoint.point))

        if (angleDifference > Math.PI / 2) {
            // we are in front of the arrow, need to mirror the pull direction
            flowDirection = flowDirection.invert().mirrorWith(flowPoint.direction)
        }

        val d = flowPoint.point.distanceTo(position) / flowPoint.radius
        val relativeAngle = flowPoint.direction.invert().difference(toFlowPoint)
        var weight = 1 - Math.pow(2.0, -d * d)
        weight *= Math.pow(Math.cos(relativeAngle / 2), 1.0 / 2)
        //val weight = 0.0
        val pullDirection = flowPoint.direction.weightedAverageWith(toFlowPoint, weight)

        return Vector.radial(pullDirection, 1.0)
    }

    private fun influenceByBorder(position: Vector): Vector {
        return Vector()
                .plus(Vector.radial(Direction(0.0), forceOfBorder(position.x))) // left border
                .plus(Vector.radial(Direction(Math.PI / 2), forceOfBorder(position.y))) // bottom border
                .plus(Vector.radial(Direction(Math.PI), forceOfBorder(IMG_WIDTH - position.x))) // right border
                .plus(Vector.radial(Direction(Math.PI * 3 / 2), forceOfBorder(IMG_HEIGHT - position.y))) // top border
    }

    private fun forceOfBorder(distance: Double): Double {
        val a = BORDER_DISTANCE
        val b = BORDER_COEFF_K
        val x = distance
        return Math.max(0.0, 2 * (Math.exp((-x) / b) - Math.exp((-a) / b)) / (1 - Math.exp((-a) / b)))
    }

}
