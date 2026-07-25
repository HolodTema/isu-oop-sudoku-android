package com.terabyte.sudokucppgame.core.domain.model

data class GameCell(
    val row: Int,
    val column: Int,
    val value: Int? = null
) {
    fun isEmpty(): Boolean {
        return value == null
    }
}
