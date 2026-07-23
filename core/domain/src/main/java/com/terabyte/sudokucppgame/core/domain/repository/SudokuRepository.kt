package com.terabyte.sudokucppgame.core.domain.repository

import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty

interface SudokuRepository {
    fun createGame(difficulty: GameDifficulty): Long

    fun makeTurn(gameId: Long, row: Int, column: Int, value: Int): Boolean

    fun getAmountMistakes(gameId: Long): Int

    fun isVictory(gameId: Long): Boolean

    fun getPuzzleField(gameId: Long): SudokuField

    fun deleteGame(gameId: Long)
}
