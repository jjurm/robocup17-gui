import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.*
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

class FlowsApp : Application() {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            Application.launch(FlowsApp::class.java, *args)
        }

        const val FILENAME = "map_day4.png";
        const val SCALE = 2.0;
        const val CANVAS_WIDTH = Flows.MAP_WIDTH * SCALE;
        const val CANVAS_HEIGHT = Flows.MAP_HEIGHT * SCALE;
        const val CANVAS_STEP = 10.0
    }

    var firstPoint: Vector? = null;

    internal var lblDescription = Label("Next action: (none)")
    internal var lblInfo = Label("0, 0")
    internal var buttons = VBox()
    internal var toggleGroup = ToggleGroup()
    internal var canvas: Canvas = Canvas(CANVAS_WIDTH, CANVAS_HEIGHT)

    val togglesMap = mutableMapOf<RadioButton, Action>()
    var nextAction: Action? = null
    var selectedEnv = Flows.defs.environments.first()
    var selectedAreaGroup = Flows.defs.areaGroups.first()

    private fun addButton(actionButton: ActionButton) {
        val btn = RadioButton(actionButton.text)
        buttons.children.add(btn)
        btn.toggleGroup = toggleGroup
        togglesMap[btn] = actionButton.action
    }

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Flows"
        val root = HBox()

        val menuEnv = MenuButton("Environment 0")
        menuEnv.items.addAll((0..Flows.defs.environments.size - 1).map { index ->
            MenuItem("Environment " + index).apply {
                onAction = EventHandler {
                    selectedEnv = Flows.defs.environments[index]
                    menuEnv.text = "Environment " + index
                    redraw()
                }
            }
        })

        val menuAreaGroup = MenuButton("Area group 0")
        menuAreaGroup.items.addAll((0..Flows.defs.areaGroups.size -1).map { i->
            MenuItem("Area group "+i).apply {
                onAction = EventHandler {
                    selectedAreaGroup = Flows.defs.areaGroups[i]
                    menuAreaGroup.text = "Area group "+i
                    redraw()
                }
            }
        })

        buttons.children.add(menuEnv)
        buttons.children.add(menuAreaGroup)
        buttons.children.add(lblInfo)
        buttons.children.add(lblDescription)

        canvas.onMouseMoved = EventHandler<MouseEvent> { e ->
            var p = Vector(e.sceneX.toInt() / SCALE, e.sceneY.toInt() / SCALE);
            p = Vector(p.x, Flows.MAP_HEIGHT - p.y)
            lblInfo.text = String.format("%d, %d", p.x.toInt(), p.y.toInt())
        }
        canvas.onMouseClicked = EventHandler<MouseEvent> { e ->
            var p = Vector(e.sceneX.toInt() / SCALE, e.sceneY.toInt() / SCALE);
            p = Vector(p.x, Flows.MAP_HEIGHT - p.y)
            nextAction = (nextAction ?: togglesMap[toggleGroup.selectedToggle])?.invoke(selectedEnv, selectedAreaGroup, p)
            lblDescription.text = "Next action: " + (nextAction?.toString() ?: "(none)")
            if (nextAction != null) {
                drawSmallMarker(p)
            } else {
                redraw()
            }
        }
        redraw()
        root.children.add(canvas)

        buttons.spacing = 5.0
        buttons.padding = Insets(10.0)

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

    private fun redraw() {
        canvas.graphicsContext2D.drawFlow(Flows.defs, selectedEnv, selectedAreaGroup)
    }

    fun drawSmallMarker(pos: Vector) = with(canvas.graphicsContext2D) {
        stroke = Color.LIME
        val size = 3
        strokeOval(toCX(pos.x) - toCR(size)/2, toCY(pos.y) - toCR(size)/2, toCR(size), toCR(size))
        lineWidth = 0.5
        strokeLine(toCX(pos.x), 0.0, toCX(pos.x), CANVAS_HEIGHT)
        strokeLine(0.0, toCY(pos.y), CANVAS_WIDTH, toCY(pos.y))
    }

    private fun storeToClipboard(string: String) {
        val stringSelection = StringSelection(string)
        val clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard()
        clpbrd.setContents(stringSelection, null)
    }

}
