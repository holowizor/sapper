package com.marekkwiecien.saper

import org.assertj.core.api.Assertions.*

import com.marekkwiecien.sapper.Board
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BoardTest {

    @Test
    fun `board is created properly`() {
        val board = Board(4, 5, 6)

        assertThat(board.sizeX).isEqualTo(4)
        assertThat(board.sizeY).isEqualTo(5)
        assertThat(board.nMines).isEqualTo(6)
    }
    
    @Test
    fun `board has given amount of mines`() {
        val board = Board(4, 5, 6)

        assertThat(board.fields.filter { it.mine }.size).isEqualTo(6)
    }
}