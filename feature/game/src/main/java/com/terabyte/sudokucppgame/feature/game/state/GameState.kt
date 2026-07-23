package com.terabyte.sudokucppgame.feature.game.state

import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty
import com.terabyte.sudokucppgame.core.domain.model.GameField

data class GameState(
    val gameId: Long,
    val field: GameField,
    val difficulty: GameDifficulty,
    val amountMistakes: Int,
    val chosenRow: Int,
    val chosenColumn: Int
)