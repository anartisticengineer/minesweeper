package game.gameView

import game.tiles.BaseTile
import game.tiles.MineTile
import game.tiles.SafeTile
import org.openrndr.math.IntVector2
import org.openrndr.shape.IntRectangle


class MineGrid(private val width: Int, private val height: Int, private val mineCount: Int) {
    var tiles: List<BaseTile> = emptyList()
    var clicks: Int = 0

    fun initialize() {
        populate()
        calculateSurroundingMines()
    }

    fun clearSurroundingSafeTiles(from: IntVector2, within: IntRectangle) {
        val xPositions = from.x - within.width until from.x + within.width
        val yPositions = from.y - within.height until from.y + within.height
        val positions = xPositions.flatMap { x -> yPositions.map { y -> IntVector2(x, y) } }
        tiles.filter { it is SafeTile && positions.contains(it.position) }.forEach { it.isOpen = true }
    }

    fun incrementCount(){
        clicks++
    }

    private fun populate() {
        val allTiles = mutableListOf<BaseTile>()
        val minePositions = minePositions()
        for (x in 0..<width) {
            for (y in 0..<height) {
                val position = IntVector2(x, y)
                val tile = if (minePositions.contains(position)) {
                    MineTile(position)
                } else {
                    SafeTile(position)
                }
                allTiles.add(tile)
            }
        }
        tiles = allTiles
    }
    //calculation of surrounding mines
    private fun calculateSurroundingMines() {
        tiles.forEach{ tile ->
            val surroundingPositions = getSurroundingPositions(tile.position)
            val surroundingMines = surroundingPositions.count { pos -> tiles.any { it is MineTile && it.matchPosition(pos) } }
            if (tile is SafeTile) {
                tile.surroundingMines = surroundingMines
            }
        }
    }

    private fun getSurroundingPositions(position: IntVector2): List<IntVector2> {
        val topLeft = position + IntVector2(-1, -1)
        val top = position + IntVector2(0, -1)
        val topRight = position + IntVector2(1, -1)
        val right = position + IntVector2(1, 0)
        val bottomRight = position + IntVector2(1, 1)
        val bottom = position + IntVector2(0, 1)
        val bottomLeft = position + IntVector2(-1, 1)
        val left = position + IntVector2(-1, 0)

        val posList = listOf(topLeft, top, topRight, right, bottomRight, bottom, bottomLeft, left)
        posList.filter {
            it.x >= 0 && it.y >= 0 && it.x < width && it.y < height
        }
        return posList
    }
    //set positions of mines
    private fun minePositions(): List<IntVector2> {
        val allPositions = List(width * height) { IntVector2(it % width, it / width) }
        val shuffledPositions = allPositions.shuffled()
        return shuffledPositions.take(mineCount)
    }
}