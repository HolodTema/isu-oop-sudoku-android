package com.terabyte.sudokucppgame.core.domain.model

import java.util.Collections.emptyList

data class GameField(
    val listGameCells: List<List<GameCell>>
) {

    operator fun get(row: Int, column: Int): GameCell {
        return listGameCells[row][column]
    }

    companion object {
        fun create(array: IntArray): GameField {
            require(
                array.size == 81
            ) {
                "Array size must be 81"
            }

            val listGameCells: MutableList<List<GameCell>> = emptyList()
            for (row in 0..8) {
                val listRow: MutableList<GameCell> = emptyList()
                for (column in 0..8) {
                    listRow.add(GameCell(row, column, array[row * 9 + column]))
                }
                listGameCells.add(listRow.toList())
            }
            return GameField(listGameCells.toList())
        }
    }
}