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

            val listGameCells: MutableList<List<GameCell>> = mutableListOf()
            for (row in 0..8) {
                val listRow: MutableList<GameCell> = mutableListOf()
                for (column in 0..8) {
                    val value = array[row * 9 + column]
                    val gameCell = if (value == 0) {
                        GameCell(row, column, null)
                    }
                    else {
                        GameCell(row, column, value)
                    }
                    listRow.add(gameCell)
                }
                listGameCells.add(listRow.toList())
            }
            return GameField(listGameCells.toList())
        }
    }
}