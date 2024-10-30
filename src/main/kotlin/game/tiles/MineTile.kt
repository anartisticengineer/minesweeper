package game.tiles

import org.openrndr.color.ColorRGBa
import org.openrndr.extra.color.presets.ORANGE
import org.openrndr.math.IntVector2

class MineTile(pos: IntVector2): BaseTile(pos) {
    override val hasMine: Boolean = true

    override val mineColor: ColorRGBa
        get() {
            if (isOpen) {
                return ColorRGBa.RED
            } else if (isFlagged) {
                return ColorRGBa.ORANGE
            }
            return ColorRGBa.GRAY
        }

    override fun onHit() {
        if (isFlagged) {
            println("Flagged Mine Tile")
        } else {
            isOpen = true
            println("You're Dead")
        }
    }
}