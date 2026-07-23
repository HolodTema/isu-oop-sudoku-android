package com.terabyte.sudokucppgame.core.domain.model

data class GameState(
    val field: GameField,
    val amountMistakes: Int,
    val isVictory: Boolean
)
