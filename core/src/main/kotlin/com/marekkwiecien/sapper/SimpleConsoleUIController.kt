package com.marekkwiecien.sapper

class SimpleConsoleDisplay : Display {

    override fun gameOver(x: Int, y: Int) {
        println("kaboom! at $x, $y there was a gameOver and now You are dead :)")
    }

    override fun neighbours(x: Int, y: Int, neighbours: Int) {
        println("for element at $x, $y there are $neighbours gameOver(s) nearby")
    }
}

class SimpleConsoleUIController {
    private val display = SimpleConsoleDisplay()
    private var sapperGame = SapperGame(5, 5, 5, display)
    private var playing = true

    fun run() {
        while (playing) {
            parseCommand(sapperGame).execute()
        }
    }

    fun newGame(sizeX: Int, sizeY: Int, mineNumber: Int) {
        sapperGame = SapperGame(sizeX, sizeY, mineNumber, display)
    }

    fun stop() {
        playing = false
    }
}
