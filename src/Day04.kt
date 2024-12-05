/**
 * --- Day 4: Ceres Search ---
 *
 * "Looks like the Chief's not here. Next!" One of The Historians pulls out a device and pushes the only button on it.
 * After a brief flash, you recognize the interior of the Ceres monitoring station!
 *
 * As the search for the Chief continues, a small Elf who lives on the station tugs on your shirt; she'd like to know
 * if you could help her with her word search (your puzzle input). She only has to find one word: XMAS.
 *
 * This word search allows words to be horizontal, vertical, diagonal, written backwards, or even overlapping other
 * words. It's a little unusual, though, as you don't merely need to find one instance of XMAS - you need to find all of
 * them. Here are a few ways XMAS might appear, where irrelevant characters have been replaced with .:
 *
 * ..X...
 * .SAMX.
 * .A..A.
 * XMAS.S
 * .X....
 *
 * The actual word search will be full of letters instead. For example:
 *
 * MMMSXXMASM
 * MSAMXMSMSA
 * AMXSXMAAMM
 * MSAMASMSMX
 * XMASAMXAMM
 * XXAMMXXAMA
 * SMSMSASXSS
 * SAXAMASAAA
 * MAMMMXMMMM
 * MXMXAXMASX
 *
 * In this word search, XMAS occurs a total of 18 times; here's the same word search again, but where letters not
 * involved in any XMAS have been replaced with .:
 *
 * ....XXMAS.
 * .SAMXMS...
 * ...S..A...
 * ..A.A.MS.X
 * XMASAMX.MM
 * X.....XA.A
 * S.S.S.S.SS
 * .A.A.A.A.A
 * ..M.M.M.MM
 * .X.X.XMASX
 *
 * Take a look at the little Elf's word search. How many times does XMAS appear?
 *
 * --- Part Two ---
 *
 * The Elf looks quizzically at you. Did you misunderstand the assignment?
 *
 * Looking for the instructions, you flip over the word search to find that this isn't actually an XMAS puzzle; it's an
 * X-MAS puzzle in which you're supposed to find two MAS in the shape of an X. One way to achieve that is like this:
 *
 * M.S
 * .A.
 * M.S
 *
 * Irrelevant characters have again been replaced with . in the above diagram. Within the X, each MAS can be written
 * forwards or backwards.
 *
 * Here's the same example from before, but this time all of the X-MASes have been kept instead:
 *
 * .M.S......
 * ..A..MSMS.
 * .M.S.MAA..
 * ..A.ASMSM.
 * .M.S.M....
 * ..........
 * S.S.S.S.S.
 * .A.A.A.A..
 * M.M.M.M.M.
 * ..........
 *
 * In this example, an X-MAS appears 9 times.
 *
 * Flip the word search from the instructions back over to the word search side and try again. How many times does an
 * X-MAS appear?
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

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
