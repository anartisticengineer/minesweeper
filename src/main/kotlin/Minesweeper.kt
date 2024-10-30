import game.gameView.MineGrid
import game.tiles.SafeTile
import org.openrndr.MouseButton
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.math.IntVector2
import org.openrndr.math.Vector2
import org.openrndr.shape.IntRectangle
import org.openrndr.shape.Rectangle

fun main() = application {
    configure {
        width = 800
        height = 600
        title = "Minesweeper"
    }

    program {
        // GLOBALS
        val mineGrid = MineGrid(20, 15, 40)
        val tileSize = 30.0
        var mousePosition = Vector2.ZERO
        mineGrid.initialize()

        mouse.moved.listen {
            mousePosition = it.position
        }

        mouse.buttonDown.listen { event ->
            if (event.button == MouseButton.LEFT) {
                val selectedTile = mineGrid.tiles.find { it.mouseOverTile(mousePosition) }
                if (selectedTile is SafeTile && (selectedTile.surroundingMines == 0 || mineGrid.clicks == 0)) {
                    mineGrid.clearSurroundingSafeTiles(selectedTile.position, IntRectangle(IntVector2.ZERO, 2, 2))
                }
                selectedTile?.onHit()
                mineGrid.incrementCount()
            } else if (event.button == MouseButton.RIGHT) {
                mineGrid.tiles.find { it.mouseOverTile(mousePosition) }?.toggleFlag()
            }
        }

        extend {
            //mine grid
            drawer.rectangles{
                mineGrid.tiles.map {
                    fill = it.mineColor
                    stroke = if (it.mouseOverTile(mousePosition)) ColorRGBa.GREEN else ColorRGBa.BLACK
                    rectangle(Rectangle(it.position.vector2 * tileSize, tileSize, tileSize))
                }
            }
            //surrounding mine counter
            for (tile in mineGrid.tiles) {
                if (tile is SafeTile && tile.surroundingMines > 0 && tile.isOpen) {
                    drawer.fill = tile.colorMap[tile.surroundingMines] ?: ColorRGBa.BLACK
                    drawer.text(tile.surroundingMines.toString(), tile.position.vector2 * tileSize + Vector2(tileSize / 2.0, tileSize / 2.0))
                }
            }
        }
    }
}