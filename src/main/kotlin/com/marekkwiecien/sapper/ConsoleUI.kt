package com.marekkwiecien.sapper

interface UIControl {
    fun gameOver(x: Int, y: Int)
    fun neighbours(x: Int, y: Int, neighbours: Int)
}

class ConsoleUIControl : UIControl {

    override fun gameOver(x: Int, y: Int) {
        println("kaboom! at $x, $y there was a gameOver and now You are dead :)")
    }

    override fun neighbours(x: Int, y: Int, neighbours: Int) {
        println("for element at $x, $y there are $neighbours gameOver(s) nearby")
    }
}

class ConsoleUI {
    private val ui = ConsoleUIControl()
    private var sapperGame = SapperGame(5, 5, 5, ui)

    fun run() {
        while (!sapperGame.end) parseCommand().execute(sapperGame)
    }

    private fun parseCommand(): Command {
        val input = readLine()!!
        return if (input.contains('p') || input.contains('b')) {
            PrintBoardCommand()
        } else if (input.contains('h')) {
            PrintHistoryCommand()
        } else if (input.contains('l')) {
            ListHistoryCommand()
        } else if (input.contains(',')) {
            val (x, y) = input.split(',').take(2).map(String::trim).map(String::toInt)
            CheckCommand(x, y)
        } else {
            NoCommand()
        }
    }
}

interface Command {
    fun execute(game: SapperGame)
}

class CheckCommand(override val x: Int, override val y: Int) : WithCoordinates, Command {
    override fun execute(game: SapperGame) {
        game.check(x, y)
    }
}

class PrintBoardCommand : Command {
    override fun execute(game: SapperGame) {
        game.board.inspectBoardFields({
            var currY = 0
            it.forEach {
                if (currY < it.y) {
                    println()
                    currY = it.y
                }
                if (it.mine) print('X') else print('_')
            }
        })
    }
}

class PrintHistoryCommand : Command {
    override fun execute(game: SapperGame) {
        game.board.inspectBoardFields({
            var currY = 0
            it.forEach {
                if (currY < it.y) {
                    println()
                    currY = it.y
                }
                if (it.visited) print(it.neighbourMines) else print('_')
            }
        })
    }
}

class ListHistoryCommand : Command {
    override fun execute(game: SapperGame) {
        game.history.forEach({ println("You visited ${it.x}, ${it.y} and there were ${game.board.neighbours(it.x, it.y)} neighbour gameOver(s)") })
    }
}

class NoCommand : Command {
    override fun execute(game: SapperGame) {
    }
}
