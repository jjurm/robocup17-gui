import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.RadioButton
import javafx.scene.control.ToggleGroup
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
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
        const val CANVAS_STEP = 8.0
    }

    var firstPoint: Vector? = null;

    internal var lblDescription = Label()
    internal var lblInfo = Label()
    internal var buttons = VBox()
    internal var toggleGroup = ToggleGroup()

    val togglesMap = mutableMapOf<RadioButton, Action>()
    var nextAction: Action? = null

    private fun addButton(actionButton: ActionButton) {
        val btn = RadioButton(actionButton.text)
        buttons.children.add(btn)
        btn.toggleGroup = toggleGroup
        togglesMap[btn] = actionButton.action
    }

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Flows"
        val root = HBox()

        buttons.children.add(lblDescription)
        buttons.children.add(lblInfo)

        val canvas = Canvas(CANVAS_WIDTH, CANVAS_HEIGHT)
        canvas.onMouseMoved = EventHandler<MouseEvent> { e ->
            var p = Vector(e.sceneX.toInt() / SCALE, e.sceneY.toInt() / SCALE);
            p = Vector(p.x, Flows.MAP_HEIGHT - p.y)
            lblInfo.text = String.format("%d, %d", p.x.toInt(), p.y.toInt())
        }
        val gc = canvas.graphicsContext2D
        canvas.onMouseClicked = EventHandler<MouseEvent> { e ->
            var p = Vector(e.sceneX.toInt() / SCALE, e.sceneY.toInt() / SCALE);
            p = Vector(p.x, Flows.MAP_HEIGHT - p.y)
            nextAction = (nextAction ?: togglesMap[toggleGroup.selectedToggle])?.invoke(Flows.defs.environments.first(), p)
            lblDescription.text = "Next action: " + (nextAction?.toString() ?: "(none)")
            gc.drawFlow(Flows.defs, Flows.defs.environments.first())
        }
        gc.drawFlow(Flows.defs, Flows.defs.environments.get(0))
        root.children.add(canvas)

        buttons.spacing = 5.0

        createActionButtons(Flows.defs).forEach { ab -> addButton(ab) }

        val export = Button("Export")
        export.setOnMouseClicked {
            generateDefinitions(Flows.defs)
        }
        buttons.children.add(export)

        root.children.add(buttons)

        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

    private fun storeToClipboard(string: String) {
        val stringSelection = StringSelection(string)
        val clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard()
        clpbrd.setContents(stringSelection, null)
    }

}
