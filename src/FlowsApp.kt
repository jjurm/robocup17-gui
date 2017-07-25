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
        const val CANVAS_STEP = 12.0
    }

    val flowPoints = mutableListOf<AnchorPoint>()
    val flowLines: List<FlowLine>

    fun _anchor(x: Int, y: Int, radius: Int) {
        flowPoints.add(AnchorPoint(x * SCALE, (IMG_HEIGHT - y) * SCALE, radius.toDouble()));
    }

    init {
        _anchor(102, 44, 40);
        _anchor(93, 58, 40);
        _anchor(110, 190, 40);
        _anchor(155, 210, 40);
        _anchor(175, 195, 40);
        _anchor(205, 113, 40);
        _anchor(160, 40, 40);


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

    private fun calculateMoveVector(position: Vector): Vector {
        val nearestFlowPoint = calculateNearestFlowPoint(position)
        return influenceByFlowPoint(position, nearestFlowPoint)
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

}
