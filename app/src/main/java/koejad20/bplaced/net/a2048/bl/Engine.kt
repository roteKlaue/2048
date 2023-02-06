package koejad20.bplaced.net.a2048.bl

// broken
class Engine {
    private val directions = mapOf(
        "left" to Pair(-1, 0),
        "right" to Pair(1, 0),
        "up" to Pair(0, -1),
        "down" to Pair(0, 1)
    )

    val grid = Array(GRID_SIZE) { IntArray(GRID_SIZE) }

    private fun combineTiles(tiles: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
        val newTiles = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until tiles.size - 1) {
            val tile1 = tiles[i]
            val tile2 = tiles[i + 1]
            if (grid[tile1.first][tile1.second] == grid[tile2.first][tile2.second]) {
                val row = tile1.first
                val col = tile1.second
                grid[row][col] *= 2
                newTiles.add(Pair(row, col))
            } else {
                newTiles.add(tile1)
            }
        }
        if (tiles.isNotEmpty()) {
            newTiles.add(tiles.last())
        }
        return newTiles
    }

    private fun moveTiles(row: Int, direction: Pair<Int, Int>): List<Pair<Int, Int>> {
        return List(grid[row].filter { it != 0 }.size) { col ->
            Pair(row + direction.first * col, col + direction.second * col)
        }.let(::combineTiles)
    }

    private fun addRandomTile() {
        val emptyTiles = ArrayList<IntArray>()
        for (i in 0 until GRID_SIZE) {
            for (j in 0 until GRID_SIZE) {
                if (grid[i][j] == 0) {
                    emptyTiles.add(intArrayOf(i, j))
                }
            }
        }
        if (emptyTiles.size > 0) {
            val randomTile = emptyTiles[(Math.random() * emptyTiles.size).toInt()]
            grid[randomTile[0]][randomTile[1]] = if (Math.random() < 0.9) 2 else 4
        }
    }

    private val isGameOver: Boolean
        get() {
            for (i in 0 until GRID_SIZE) {
                for (j in 0 until GRID_SIZE) {
                    if (grid[i][j] == 0) {
                        return false
                    }
                    if (i > 0 && grid[i][j] == grid[i - 1][j]) {
                        return false
                    }
                    if (i < GRID_SIZE - 1 && grid[i][j] == grid[i + 1][j]) {
                        return false
                    }
                    if (j > 0 && grid[i][j] == grid[i][j - 1]) {
                        return false
                    }
                    if (j < GRID_SIZE - 1 && grid[i][j] == grid[i][j + 1]) {
                        return false
                    }
                }
            }
            return true
        }

    fun start() {
        addRandomTile()
        addRandomTile()
    }

    fun move(direction: String) {
        val d = directions[direction] ?: return
        for (row in 0 until GRID_SIZE) {
            val movedTiles = moveTiles(row, d)
            movedTiles.forEachIndexed { index, tile ->
                grid[tile.first][tile.second] = grid[row][index]
                grid[row][index] = 0
            }
        }
    }

    val isWon: Boolean
        get() {
            for (i in 0 until GRID_SIZE) {
                for (j in 0 until GRID_SIZE) {
                    if (grid[i][j] == 2048) {
                        return true
                    }
                }
            }
            return false
        }

    val isOver: Boolean
        get() = isGameOver && !isWon



    companion object {
        private const val GRID_SIZE = 4
    }
}