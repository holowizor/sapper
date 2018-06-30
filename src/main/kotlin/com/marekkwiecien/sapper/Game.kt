package com.marekkwiecien.sapper

import java.util.*

typealias BoardFieldsInspector = (Array<Field>) -> Unit

class Board(val sizeX: Int, val sizeY: Int, val nMines: Int) {
    private val fields: Array<Field>

    init {
        val mines = randomizeMines(nMines, sizeX * sizeY - 1)
        fields = Array(sizeX * sizeY, {
            val x = it % sizeX
            val y = it / sizeX
            Field(x, y, isMine(x, y, mines), calculateNeighbourMines(x, y, mines))
        })
    }

    fun check(x: Int, y: Int) = !fields[index(x, y)].mine
    fun neighbours(x: Int, y: Int) = fields[index(x, y)].neighbourMines
    fun inspectBoardFields(boardFieldsInspector: BoardFieldsInspector) = boardFieldsInspector(fields)

    private fun randomizeMines(nMines: Int, size: Int): List<Mine> = (0..size).toList().shuffled().take(nMines).map { Mine(it % sizeX, it / sizeX) }.toList()
    private fun isMine(x: Int, y: Int, mines: List<Mine>) = mines.filter { it.x == x && it.y == y }.any()
    private fun calculateNeighbourMines(x: Int, y: Int, mines: List<Mine>) = mines.filter { it != Mine(x, y) && Math.abs(it.x - x) <= 1 && Math.abs(it.y - y) <= 1 }.count()
    private fun index(x: Int, y: Int) = y * sizeX + x
}

interface Display {
    fun gameOver(x: Int, y: Int)
    fun neighbours(x: Int, y: Int, neighbours: Int)
}

class History : LinkedList<Point>()

class SapperGame(sizeX: Int, sizeY: Int, mineNumber: Int, private val display: Display) {
    val board = Board(sizeX, sizeY, mineNumber)
    val history = History()
    var end = false
        private set

    fun check(x: Int, y: Int) {
        history.push(Point(x, y))
        if (board.check(x, y)) {
            val neighbours = board.neighbours(x, y)
            display.neighbours(x, y, neighbours)
        } else {
            display.gameOver(x, y)
            end = true
        }
    }
}
