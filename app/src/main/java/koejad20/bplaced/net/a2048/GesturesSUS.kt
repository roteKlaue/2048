package koejad20.bplaced.net.a2048

import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent

class GesturesSUS(private val context: Game) : SimpleOnGestureListener() {
    private val map: Map<Number, Map<Number, String>> = mapOf(
            1 to mapOf(
                0 to "left",
                -1 to "error",
                1 to "error"
            ),
            -1 to mapOf(
                0 to "right",
                -1 to "error",
                1 to "error"
            ),
            0 to mapOf(
                0 to "error",
                1 to "up",
                -1 to "down"
            )
        )

    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        val tempX = e1.x - e2.x
        val tempY = e1.y - e2.y
        val x = if (tempX > 100) 1 else if (tempX < -100) -1 else 0
        val y = if (tempY > 100) 1 else if (tempY < -100) -1 else 0

        val type = map[x]?.get(y)

        if (type != "error" && type != null) {
            context.engine.move(type)
            context.update()
        }

        return super.onFling(e1, e2, velocityX, velocityY)
    }
}
