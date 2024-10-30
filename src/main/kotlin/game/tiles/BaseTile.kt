package game.tiles

import org.openrndr.color.ColorRGBa
import org.openrndr.math.IntVector2
import org.openrndr.math.Vector2
import org.openrndr.shape.Rectangle

abstract class BaseTile(private val pos: IntVector2) {
    protected var isFlagged = false
    var isOpen = false
    private val tileSize = 30.0
    abstract val hasMine: Boolean
    abstract val mineColor: ColorRGBa
    abstract fun onHit()

    fun toggleFlag() {
        isFlagged = !isFlagged
    }

    fun matchPosition(position: IntVector2): Boolean {
        return pos == position
    }

    fun mouseOverTile(mousePosition: Vector2): Boolean {
        return Rectangle(pos.vector2 * tileSize, tileSize, tileSize).contains(mousePosition)
    }

    val position: IntVector2
        get() = pos
}