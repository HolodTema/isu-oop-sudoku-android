package com.terabyte.sudokucppgame.core.domain.model

data class GameCell(
    val value: Int? = null
) {

    fun isEmpty(): Boolean {
        return value == null
    }

    fun isFilled(): Boolean {
        return value != null
    }
}
