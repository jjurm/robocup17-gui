import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.TextArea
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

class FlowsApp : Application() {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            Application.launch(FlowsApp::class.java, *args)
        }

        const val FILENAME = "map_day3.png";
        const val SCALE = 2.0;
        const val CANVAS_WIDTH = Flows.MAP_WIDTH * SCALE;
        const val CANVAS_HEIGHT = Flows.MAP_HEIGHT * SCALE;
        const val CANVAS_STEP = 7.0
    }

    var firstPoint: Vector? = null;

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Flows"
        val root = Group()

        val text = TextArea()
        text.layoutY = CANVAS_HEIGHT
        text.maxHeight = 30.0
        text.maxWidth = 100.0

        val output = TextArea();
        output.layoutY = CANVAS_HEIGHT + text.maxHeight
        output.maxHeight = 60.0;

        val canvas = Canvas(CANVAS_WIDTH, CANVAS_HEIGHT)
        canvas.onMouseMoved = EventHandler<MouseEvent> { e ->
            var p = Vector(e.sceneX.toInt() / SCALE, e.sceneY.toInt() / SCALE);
            p = Vector(p.x, Flows.MAP_HEIGHT - p.y)
            text.text = String.format("%d, %d", p.x.toInt(), p.y.toInt())
        }
        canvas.onMouseClicked = EventHandler<MouseEvent> { e ->
            var p = Vector(e.sceneX.toInt() / SCALE, e.sceneY.toInt() / SCALE);
            p = Vector(p.x, Flows.MAP_HEIGHT - p.y)
            val pa = firstPoint;
            if (pa == null) {
                firstPoint = p;
            } else {
                val radius = pa.distanceTo(p) * 2
                val direction = pa.directionTo(p)
                output.text += String.format("_environment_route_point(%d, %d, %d, %d);\n",
                        pa.x.toInt(), pa.y.toInt(), radius.toInt(), direction.degreesNice());
                Flows.environments.get(DRAW_ENVIRONMENT).flowPoints.add(FlowPoint(Vector(pa.x, pa.y), radius, direction))

                val gc = canvas.graphicsContext2D
                gc.clearRect(0.0, 0.0, canvas.getWidth(), canvas.getHeight())
                gc.drawFlow(Flows.environments.get(0))
                firstPoint = null;
            }
        }
        val gc = canvas.graphicsContext2D
        gc.drawFlow(Flows.environments.get(0))
        root.children.add(canvas)

        root.children.add(text)
        root.children.add(output)

        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

    private fun storeToClipboard(string: String) {
        val stringSelection = StringSelection(string)
        val clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard()
        clpbrd.setContents(stringSelection, null)
    }

    private fun createButtons(buttons: List<ActionButton>) {
        TODO()
    }

}
