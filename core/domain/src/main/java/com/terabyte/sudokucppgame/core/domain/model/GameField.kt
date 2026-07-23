package com.terabyte.sudokucppgame.core.domain.model

data class GameField(
    val listGameCells: List<List<GameCell>>
) {

    init {

    }

    operator fun get(row: Int, column: Int): GameCell {
        return listGameCells[row][column]
    }

    fun toFlatList(): List<GameCell> {
        return listGameCells.flatten()
    }

    fun toIntArray(): IntArray {
        return toFlatList().map {
            it.value ?: 0
        }.toIntArray()
    }

    companion object {
        fun create(array: IntArray): GameField {
            require(
                array.size == 81
            ) {
                "Array size must be 81"
            }

            val listGameCells = array.map {
                val value = if (it == 0) {
                    null
                }
                else {
                    it
                }
                GameCell(value)
            }
            return GameField(listGameCells.chunked(9))
        }
    }
}