package com.marekkwiecien.saper

import com.marekkwiecien.sapper.Board
import com.marekkwiecien.sapper.Field
import org.assertj.core.api.Assertions.assertThat
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

        var nMines = 0
        board.inspectBoardFields { nMines = it.filter { it.mine }.size }

        assertThat(nMines).isEqualTo(6)
    }

    @Test
    fun `board gives proper neighbour mines information`() {
        val board = Board(4, 5, 6)

        board.inspectBoardFields {
            it.forEach { external ->
                val neighboursOfExternal = ArrayList<Field>()

                board.inspectBoardFields {
                    neighboursOfExternal.addAll(it.filter { internal -> internal != external && Math.abs(internal.x - external.x) <= 1 && Math.abs(internal.y - external.y) <= 1 })
                }
                assertThat(neighboursOfExternal.filter { it.mine }.size).isEqualTo(external.neighbourMines)
            }
        }
    }
}