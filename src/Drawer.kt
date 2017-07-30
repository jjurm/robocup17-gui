import FlowsApp.Companion.CANVAS_HEIGHT
import FlowsApp.Companion.CANVAS_STEP
import FlowsApp.Companion.CANVAS_WIDTH
import FlowsApp.Companion.FILENAME
import FlowsApp.Companion.SCALE
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import java.io.FileInputStream

fun toCX(posX: Double): Double = posX * SCALE
fun toCX(posX: Int): Double = posX * SCALE
fun toCY(posY: Double): Double = CANVAS_HEIGHT - posY * SCALE
fun toCY(posY: Int): Double = CANVAS_HEIGHT - posY * SCALE
fun toCR(r: Double) = r * SCALE
fun toCR(r: Int) = r * SCALE

fun GraphicsContext.drawFlow(defs: DefinitionHolder, env: Environment, areaGroup: AreaGroup) {
    val image = javafx.scene.image.Image(FileInputStream(FILENAME))
    drawImage(image, 0.0, 0.0, image.width, image.height, 0.0, 0.0, CANVAS_WIDTH, CANVAS_HEIGHT)

    // Walls
    for (wall in defs.walls) {
        lineWidth = 3.0
        stroke = Color.VIOLET
        strokeLine(toCX(wall.a.x), toCY(wall.a.y), toCX(wall.b.x), toCY(wall.b.y))
    }

    // Areas
    for (area in areaGroup.areas) {
        stroke = Color.YELLOW
        lineWidth = 3.0
        drawArea(area)
    }

    // SuperobjectRules
    for (rule in defs.superobjectRules) {
        lineWidth = 2.0
        stroke = Color.BLUE
        drawArea(rule.area)
        stroke = Color.GREEN
        drawArea(rule.entry)
        drawRuleRoute(rule)
    }

    drawEnv(env)
    drawPointers(env)
}

fun GraphicsContext.drawEnv(env: Environment) {
    lineWidth = 2.0
    stroke = Color.BLACK
    strokeRect(0.0, 0.0, CANVAS_WIDTH, CANVAS_HEIGHT)

    // FlowLines
    var i = 0;
    while (i < env.anchors.size) {
        val a = env.anchors[i]
        val b = env.anchors[(i + 1) % env.anchors.size]

        stroke = Color.RED;
        lineWidth = 2.0;
        drawAnchor(a)

        lineWidth = 1.0
        strokeLine(toCX(a.point.x), toCY(a.point.y), toCX(b.point.x), toCY(b.point.y))

        i++
    }

    // Radiuses
    stroke = Color.RED;
    lineWidth = 2.0;
    val SIZE = 10.0;
    for (flowPoint in env.flowPoints) {
        val offsetter = Vector.radial(flowPoint.direction, SIZE);

        beginPath();
        val bottom = flowPoint.point.minus(offsetter);
        moveTo(toCX(bottom.x), toCY(bottom.y));
        val top = flowPoint.point.plus(offsetter);
        lineTo(toCX(top.x), toCY(top.y));
        stroke();

        beginPath();
        val left = top.minus(Vector.radial(flowPoint.direction.plus(Direction.fromDegrees(30.0)), SIZE));
        val right = top.minus(Vector.radial(flowPoint.direction.minus(Direction.fromDegrees(30.0)), SIZE));
        moveTo(toCX(left.x), toCY(left.y));
        lineTo(toCX(top.x), toCY(top.y));
        lineTo(toCX(right.x), toCY(right.y));
        stroke();

        strokeOval(
                toCX(flowPoint.point.x) - toCR(flowPoint.radius) / 2,
                toCY(flowPoint.point.y) - toCR(flowPoint.radius) / 2,
                toCR(flowPoint.radius),
                toCR(flowPoint.radius))
    }

    // FlowRoutes
    for (flowRoute in env.flowRoutes) {

        i = 0;
        while (i < flowRoute.points.size - 1) {
            val a = flowRoute.points[i]
            val b = flowRoute.points[i + 1]

            stroke = Color.AQUA;
            lineWidth = 2.0;
            drawAnchor(a)
            if (i + 2 == flowRoute.points.size) {
                drawAnchor(b)
            }

            lineWidth = 1.0
            strokeLine(toCX(a.point.x), toCY(a.point.y), toCX(b.point.x), toCY(b.point.y))

            i++
        }

    }
}

fun GraphicsContext.drawPointers(env: Environment) {
    // Draw markers
    var x = CANVAS_STEP / 2
    while (x < CANVAS_WIDTH) {
        var y = CANVAS_STEP / 2
        while (y < CANVAS_HEIGHT) {
            val position = Vector(x, y)

            val finalVector = Flows.calculateMoveVector(env, position)

            val MARK_SIZE = 2.0
            val drawVector = Vector.radial(finalVector.direction(), toCR(MARK_SIZE)).invert();

            lineWidth = 1.0
            stroke = Color.BLACK
            val RADIUS = 1.0
            strokeOval(toCX(position.x) - toCR(RADIUS) / 2, toCY(position.y) - toCR(RADIUS) / 2, toCR(RADIUS), toCR(RADIUS));

            stroke = Color.rgb(50, 50, 50)
            beginPath();
            moveTo(toCX(position.x), toCY(position.y));
            lineTo(toCX(position.x + drawVector.x), toCY(position.y + drawVector.y));
            stroke();

            y += CANVAS_STEP
        }
        x += CANVAS_STEP
    }
}

fun GraphicsContext.drawArea(r: Area) {
    strokeRect(toCX(r.xa), toCY(maxOf(r.ya, r.yb)), toCR(r.xb - r.xa), toCR(Math.abs(r.yb - r.ya)))
}

fun GraphicsContext.drawRoute(r: Route) {
    var i = 0;
    stroke = Color.ORANGE
    while (i < r.points.size - 1) {
        val pa = r.points.get(i)
        val pb = r.points.get(i + 1)
        strokeLine(toCX(pa.x), toCY(pa.y), toCX(pb.x), toCY(pb.y))
        i++
    }
}

fun GraphicsContext.drawRuleRoute(r: SuperobjectRule) {
    stroke = Color.ORANGE
    val list = mutableListOf(r.entry.center)
    list.addAll(r.route.points)
    list.add(r.area.center)
    var i = 0;
    while (i < list.size - 1) {
        val pa = list.get(i)
        val pb = list.get(i + 1)
        strokeLine(toCX(pa.x), toCY(pa.y), toCX(pb.x), toCY(pb.y))
        i++
    }
}

fun GraphicsContext.drawAnchor(o: Anchor) {
    strokeOval(toCX(o.point.x) - toCR(o.radius) / 2, toCY(o.point.y) - toCR(o.radius) / 2, toCR(o.radius), toCR(o.radius))
}


