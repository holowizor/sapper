package com.marekkwiecien.sapper

 fun SimpleConsoleUIController.parseCommand(sapperGame: SapperGame): Command {
    val input = readLine()!!
    return if (input.contains('n')) {
        NewGameCommand(this, input)
    } else if (input.contains('x')) {
        ExitGameCommand(this)
    } else if (input.contains('p') || input.contains('b')) {
        PrintBoardCommand(sapperGame)
    } else if (input.contains('h')) {
        PrintHistoryCommand(sapperGame)
    } else if (input.contains('l')) {
        ListHistoryCommand(sapperGame)
    } else if (input.contains(',')) {
        val (x, y) = input.split(',').take(2).map(String::trim).map(String::toInt)
        CheckCommand(x, y, sapperGame)
    } else {
        NoCommand()
    }
}

interface Command {
    fun execute()
}

class NoCommand : Command {
    override fun execute() {
    }
}

class NewGameCommand(private val controller: SimpleConsoleUIController, private val input: String) : Command {
    override fun execute() {
        when (input) {
            "n3" -> controller.newGame(10, 10, 20)
            "n2" -> controller.newGame(7, 7, 14)
            else -> controller.newGame(5, 5, 5)
        }
    }
}

class ExitGameCommand(private val controller: SimpleConsoleUIController) : Command {
    override fun execute() {
        controller.stop()
    }
}

open abstract class UICommand(protected val sapperGame: SapperGame) : Command

class CheckCommand(override val x: Int, override val y: Int, sapperGame: SapperGame) : WithCoordinates, UICommand(sapperGame) {
    override fun execute() {
        sapperGame.check(x, y)
    }
}

class PrintBoardCommand(sapperGame: SapperGame) : UICommand(sapperGame) {
    override fun execute() {
        sapperGame.board.inspectBoardFields({
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

class PrintHistoryCommand(sapperGame: SapperGame) : UICommand(sapperGame) {
    override fun execute() {
        sapperGame.board.inspectBoardFields({
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

class ListHistoryCommand(sapperGame: SapperGame) : UICommand(sapperGame) {
    override fun execute() {
        sapperGame.history.forEach({ println("You visited ${it.x}, ${it.y} and there were ${sapperGame.board.neighbours(it.x, it.y)} neighbour gameOver(s)") })
    }
}
