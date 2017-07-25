package flows

fun main(args: Array<String>) {

}

class Drawer {
    companion object {
        const val CANVAS_STEP = 12.0;
    }

    fun redraw() {
        /*val canvas = document.getElementById("canvas") as HTMLCanvasElement;
        val ctx = canvas.getContext("2d") as CanvasRenderingContext2D;

        ctx.rect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble());
        ctx.stroke();

        // Draw markers
        for (var x = CANVAS_STEP / 2; x < canvas.width; x += CANVAS_STEP) {
            val a = 5
            for (var y = CANVAS_STEP / 2; y < canvas.height; y += CANVAS_STEP) {



            val finalVector = Vector();
            for (i = 0; i < flowPoints.length; i++) {
            let flowPoint = flowPoints [i];
            let toFlowPoint = position . directionTo (flowPoint.point);

            let force = Math . abs (/*Math.cos(toFlowPoint.difference(flowPoint.direction))*/ 1)
            / Math.pow(flowPoint.point.distanceTo(position) / 100, 3);

            let pullDirection = toFlowPoint;
            if (flowPoint.direction.difference(position.directionTo(flowPoint.point)) > Math.PI / 2) {
                // we are in front of the arrow, need to mirror the pull direction
                pullDirection = pullDirection.invert().mirrorWith(flowPoint.direction)
            }
            pullDirection = pullDirection.weightedAverageWith(flowPoint.direction, 0.4);

            finalVector = finalVector.plus(Vector.radial(pullDirection, force));
        }


            const MARK_SIZE = 6;
            let drawVector = Vector . radial (finalVector.direction(), MARK_SIZE).invert();

            ctx.strokeStyle = "#666";
            ctx.beginPath();
            let radius = 1;
            ctx.arc(position.x, position.y, radius, 0, 2 * Math.PI);
            ctx.stroke();

            ctx.strokeStyle = "#777";
            ctx.beginPath();
            ctx.moveTo(position.x, position.y);
            ctx.lineTo(position.x + drawVector.x, position.y + drawVector.y);
            ctx.stroke();

        }
        }

        // Draw flow points
        for (let flowPoint of flowPoints) {
            let size = 15;
            let offsetter = Vector . radial (flowPoint.direction, size);

            ctx.strokeStyle = "#FF0000";
            ctx.lineWidth = 2;
            ctx.beginPath();
            let bottom = flowPoint . point . minus (offsetter);
            ctx.moveTo(bottom.x, bottom.y);
            let top = flowPoint . point . plus (offsetter);
            ctx.lineTo(top.x, top.y);
            ctx.stroke();

            ctx.beginPath();
            let left = top . minus (Vector.radial(flowPoint.direction.plus(Direction.fromDegrees(30)), size));
            let right = top . minus (Vector.radial(flowPoint.direction.minus(Direction.fromDegrees(30)), size));
            ctx.moveTo(left.x, left.y);
            ctx.lineTo(top.x, top.y);
            ctx.lineTo(right.x, right.y);
            ctx.stroke();
        }



        }
        }*/

    }
}