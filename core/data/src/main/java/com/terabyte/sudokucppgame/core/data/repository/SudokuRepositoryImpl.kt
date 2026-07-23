package com.terabyte.sudokucppgame.core.data.repository

import com.terabyte.sudokucppgame.core.data.jni.SudokuNative
import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty
import com.terabyte.sudokucppgame.core.domain.model.GameField
import com.terabyte.sudokucppgame.core.domain.repository.SudokuRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SudokuRepositoryImpl @Inject constructor(
    private val sudokuNative: SudokuNative
): SudokuRepository {

    override fun createGame(difficulty: GameDifficulty): Long {
        return sudokuNative.createGame(difficulty.value)
    }

    override fun makeTurn(
        gameId: Long,
        row: Int,
        column: Int,
        value: Int
    ): Boolean {
        return sudokuNative.makeTurn(gameId, row, column, value)
    }

    override fun getAmountMistakes(gameId: Long): Int {
        return sudokuNative.getMistakes(gameId)
    }

    override fun isVictory(gameId: Long): Boolean {
        return sudokuNative.isVictory(gameId)
    }

    override fun getPuzzleField(gameId: Long): GameField {
        return GameField.create(sudokuNative.getPuzzleField(gameId))
    }

    override fun deleteGame(gameId: Long) {
        sudokuNative.deleteGame(gameId)
    }
}