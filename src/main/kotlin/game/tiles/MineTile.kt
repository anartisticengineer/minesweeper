package game.tiles

import org.openrndr.color.ColorRGBa
import org.openrndr.math.IntVector2

class MineTile(pos: IntVector2): BaseTile(pos) {
    override val hasMine: Boolean = true

    override val mineColor: ColorRGBa
        get() = if (isOpen) ColorRGBa.RED else ColorRGBa.GRAY

    override fun onHit() {
        if (isFlagged) {
            println("Flagged Mine Tile")
        } else {
            isOpen = true
            println("You're Dead")
        }
    }
}