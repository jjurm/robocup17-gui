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

        const val FILENAME = "img.png";
        const val SCALE = 2.0;
        const val CANVAS_WIDTH = Flows.MAP_WIDTH * SCALE;
        const val CANVAS_HEIGHT = Flows.MAP_HEIGHT * SCALE;
        const val CANVAS_STEP = 7.0
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
            p = Vector(p.x, Flows.MAP_HEIGHT - p.y)
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
    private fun toCX(posX: Int): Double = posX * SCALE
    private fun toCY(posY: Double): Double = CANVAS_HEIGHT - posY * SCALE
    private fun toCY(posY: Int): Double = CANVAS_HEIGHT - posY * SCALE
    private fun toCR(r: Double) = r * SCALE
    private fun toCR(r: Int) = r * SCALE

    private fun drawShapes(gc: GraphicsContext) {
        val image = javafx.scene.image.Image(FileInputStream(FILENAME))
        gc.drawImage(image, 0.0, 0.0, image.width, image.height, 0.0, 0.0, CANVAS_WIDTH, CANVAS_HEIGHT)
        drawEnv(gc, Flows.environments.get(DRAW_ENVIRONMENT))

        // SuperobjectRules
        for (rule in Flows.superobjectRules) {
            gc.lineWidth = 2.0
            gc.stroke = Color.BLUE
            gc.drawArea(rule.area)
            gc.stroke = Color.GREEN
            gc.drawArea(rule.entry)
            gc.drawRuleRoute(rule)
        }

        // Walls
        for (wall in Flows.walls) {
            gc.lineWidth = 3.0
            gc.stroke = Color.VIOLET
            gc.strokeLine(toCX(wall.a.x), toCY(wall.a.y), toCX(wall.b.x), toCY(wall.b.y))
        }
    }

    private fun drawEnv(gc: GraphicsContext, env: Environment) {
        gc.lineWidth = 2.0
        gc.stroke = Color.BLACK
        gc.strokeRect(0.0, 0.0, CANVAS_WIDTH, CANVAS_HEIGHT)

        // Draw markers
        var x = CANVAS_STEP / 2
        while (x < CANVAS_WIDTH) {
            var y = CANVAS_STEP / 2
            while (y < CANVAS_HEIGHT) {
                val position = Vector(x, y)

                val finalVector = Flows.calculateMoveVector(env, position)

                val MARK_SIZE = 2.0
                val drawVector = Vector.radial(finalVector.direction(), toCR(MARK_SIZE)).invert();

                gc.lineWidth = 1.0
                gc.stroke = Color.BLACK
                val RADIUS = 1.0
                gc.strokeOval(toCX(position.x) - toCR(RADIUS) / 2, toCY(position.y) - toCR(RADIUS) / 2, toCR(RADIUS), toCR(RADIUS));

                gc.stroke = Color.rgb(50, 50, 50)
                gc.beginPath();
                gc.moveTo(toCX(position.x), toCY(position.y));
                gc.lineTo(toCX(position.x + drawVector.x), toCY(position.y + drawVector.y));
                gc.stroke();

                y += CANVAS_STEP
            }
            x += CANVAS_STEP
        }

        // FlowLines
        var i = 0;
        while (i < env.anchors.size) {
            val a = env.anchors[i]
            val b = env.anchors[(i + 1) % env.anchors.size]

            gc.stroke = Color.RED;
            gc.lineWidth = 2.0;
            gc.drawAnchor(a)

            gc.lineWidth = 1.0
            gc.strokeLine(toCX(a.point.x), toCY(a.point.y), toCX(b.point.x), toCY(b.point.y))

            i++
        }

        // Radiuses
        gc.stroke = Color.RED;
        gc.lineWidth = 2.0;
        val SIZE = 10.0;
        for (flowPoint in env.flowPoints) {
            val offsetter = Vector.radial(flowPoint.direction, SIZE);

            gc.beginPath();
            val bottom = flowPoint.point.minus(offsetter);
            gc.moveTo(toCX(bottom.x), toCY(bottom.y));
            val top = flowPoint.point.plus(offsetter);
            gc.lineTo(toCX(top.x), toCY(top.y));
            gc.stroke();

            gc.beginPath();
            val left = top.minus(Vector.radial(flowPoint.direction.plus(Direction.fromDegrees(30.0)), SIZE));
            val right = top.minus(Vector.radial(flowPoint.direction.minus(Direction.fromDegrees(30.0)), SIZE));
            gc.moveTo(toCX(left.x), toCY(left.y));
            gc.lineTo(toCX(top.x), toCY(top.y));
            gc.lineTo(toCX(right.x), toCY(right.y));
            gc.stroke();

            gc.strokeOval(
                    toCX(flowPoint.point.x) - toCR(flowPoint.radius) / 2,
                    toCY(flowPoint.point.y) - toCR(flowPoint.radius) / 2,
                    toCR(flowPoint.radius),
                    toCR(flowPoint.radius))
        }

        // FlowRoutes
        for (flowRoute in env.flowRoutes) {

            i = 0;
            while (i < flowRoute.points.size-1) {
                val a = flowRoute.points[i]
                val b = flowRoute.points[i+1]

                gc.stroke = Color.AQUA;
                gc.lineWidth = 2.0;
                gc.drawAnchor(a)
                if (i+2 == flowRoute.points.size) {
                    gc.drawAnchor(b)
                }

                gc.lineWidth = 1.0
                gc.strokeLine(toCX(a.point.x), toCY(a.point.y), toCX(b.point.x), toCY(b.point.y))

                i++
            }

        }
    }

    private fun GraphicsContext.drawArea(r: Area) {
        strokeRect(toCX(r.xa), toCY(maxOf(r.ya, r.yb)), toCR(r.xb - r.xa), toCR(Math.abs(r.yb - r.ya)))
    }

    private fun GraphicsContext.drawRoute(r: Route) {
        var i = 0;
        stroke = Color.ORANGE
        while (i < r.points.size - 1) {
            val pa = r.points.get(i)
            val pb = r.points.get(i+1)
            strokeLine(toCX(pa.x), toCY(pa.y), toCX(pb.x), toCY(pb.y))
            i++
        }
    }

    private fun GraphicsContext.drawRuleRoute(r: SuperobjectRule) {
        stroke = Color.ORANGE
        val list = mutableListOf(r.entry.center)
        list.addAll(r.route.points)
        list.add(r.area.center)
        var i = 0;
        while (i < list.size - 1) {
            val pa = list.get(i)
            val pb = list.get(i+1)
            strokeLine(toCX(pa.x), toCY(pa.y), toCX(pb.x), toCY(pb.y))
            i++
        }
    }

    private fun GraphicsContext.drawAnchor(o: Anchor) {
        strokeOval(toCX(o.point.x) - toCR(o.radius) / 2, toCY(o.point.y) - toCR(o.radius) / 2, toCR(o.radius), toCR(o.radius))
    }

}
