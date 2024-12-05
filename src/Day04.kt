/**
 * --- Day 4:  ---
 */

enum class DIR {
    UP, DOWN, LEFT, RIGHT,
    UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT
}

fun main() {

    fun buildGrid(input: List<String>): List<List<Char>> {
        val grid = mutableListOf(listOf<Char>())

        for (line in input) {
            val row = mutableListOf<Char>()

            for (i in line.indices) {
                row.add(line[i])
            }

            grid.add(row)
        }

        return grid
    }

    fun getIncrements(dir: DIR): Pair<Int, Int> {
        return when (dir) {
            DIR.UP -> Pair(-1, 0)
            DIR.DOWN -> Pair(1, 0)
            DIR.LEFT -> Pair(0, -1)
            DIR.RIGHT -> Pair(0, 1)
            DIR.UP_LEFT -> Pair(-1, -1)
            DIR.UP_RIGHT -> Pair(-1, 1)
            DIR.DOWN_LEFT -> Pair(1, -1)
            DIR.DOWN_RIGHT -> Pair(1, 1)
        }
    }

    fun findXmas(row: Int, col: Int, grid: List<List<Char>>, dir: DIR): Boolean {
        val inc = getIncrements(dir)

        val stringBuilder = StringBuilder()
        var r = row
        var c = col
        while (r > 0 && r < grid.size && c >= 0 && c < grid[r].size && stringBuilder.length < 4) {
           stringBuilder.append(grid[r][c])
            r += inc.first
            c += inc.second
        }

        return stringBuilder.toString() == "XMAS"
    }

    fun countXmas(row: Int, col: Int, grid: List<List<Char>>): Int {
        return DIR.entries
            .map { findXmas(row, col, grid, it) }
            .count { it }
    }

    fun countMas(row: Int, col: Int, grid: List<List<Char>>): Int {
        val masUpperLeft = if (grid[row-1][col-1] == 'M' && grid[row+1][col+1] == 'S') 1 else 0
        val masBottomLeft = if (grid[row+1][col-1] == 'M' && grid[row-1][col+1] == 'S') 1 else 0
        val masUpperRight = if (grid[row-1][col+1] == 'M' && grid[row+1][col-1] == 'S') 1 else 0
        val masBottomRight = if (grid[row+1][col+1] == 'M' && grid[row-1][col-1] == 'S') 1 else 0

        return if ((masUpperLeft + masBottomLeft + masUpperRight + masBottomRight) == 2) 1 else 0
    }

    fun part1(input: List<String>): Int {
        val grid = buildGrid(input)
        var xmas = 0

        for ((row, rows) in grid.withIndex()) {
            for ((col, char) in rows.withIndex()) {
                if (char == 'X') {
                    xmas += countXmas(row, col, grid)
                }
            }
        }

        return xmas
    }

    fun part2(input: List<String>): Int  {
        val grid = buildGrid(input)
        var mas = 0

        for ((row, rows) in grid.withIndex()) {
            if (row == 1 || row >= grid.size -1) continue
            for ((col, char) in rows.withIndex()) {
                if (col == 0 || col >= grid[row].size - 1) continue
                if (char == 'A') {
                    mas += countMas(row, col, grid)
                }
            }
        }

        return mas
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
