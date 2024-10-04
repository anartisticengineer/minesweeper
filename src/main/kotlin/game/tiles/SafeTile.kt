package game.tiles

import org.openrndr.color.ColorRGBa
import org.openrndr.extra.color.presets.*
import org.openrndr.math.IntVector2

class SafeTile(pos: IntVector2) : BaseTile(pos) {
    override val hasMine: Boolean = false

    override val mineColor: ColorRGBa
        get() = if (isOpen) ColorRGBa.WHITE else ColorRGBa.GRAY

    override fun onHit() {
        if (isFlagged) {
            println("Flagged Safe Tile")
        } else {
            isOpen = true
            println("Safe Tile")
        }
    }

    val colorMap = mapOf(1 to ColorRGBa.BLUE, 2 to ColorRGBa.DARK_GREEN,
        3 to ColorRGBa.RED, 4 to ColorRGBa.NAVY,
        5 to ColorRGBa.DARK_RED, 6 to ColorRGBa.DEEP_PINK,
        7 to ColorRGBa.GOLDENROD, 8 to ColorRGBa.BLACK)

    var surroundingMines: Int = 0
        set(value) {
            field = if (value < 0) 0 else value
        }
}