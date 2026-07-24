package com.terabyte.sudokucppgame.core.data.repository

import com.terabyte.sudokucppgame.core.data.jni.SudokuNative
import com.terabyte.sudokucppgame.core.domain.model.GameCell
import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty
import com.terabyte.sudokucppgame.core.domain.model.GameField
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Test


class SudokuRepositoryImplTest {
    private val sudokuNative: SudokuNative = mockk()
    private val repository = SudokuRepositoryImpl(sudokuNative)

    @Test
    fun `createGame() calls SudokuNative createGame() and returns gameId`() {
        // arrange
        val difficulty = GameDifficulty.EASY
        val expectedGameId = 2L
        every {
            sudokuNative.createGame(difficulty.value)
        } returns expectedGameId

        // act
        val gameId = repository.createGame(difficulty)

        // assert
        verify(exactly = 1) {
            sudokuNative.createGame(difficulty.value)
        }
        assertEquals(expectedGameId, gameId)
    }

    @Test
    fun `makeTurn() calls SudokuNative makeTurn() and returns boolean`() {
        // arrange
        val gameId = 1L
        val row = 1
        val column = 1
        val value = 1
        val expectedResult = true
        every {
            sudokuNative.makeTurn(gameId, row, column, value)
        } returns expectedResult

        // act
        val result = repository.makeTurn(gameId, row, column, value)

        // assert
        verify(exactly = 1) {
            sudokuNative.makeTurn(gameId, row, column, value)
        }
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getAmountMistakes() calls SudokuNative getMistakes() and returns int`() {
        // arrange
        val gameId = 1L
        val expectedAmountMistakes = 5
        every { sudokuNative.getMistakes(gameId) } returns expectedAmountMistakes

        // act
        val amountMistakes = repository.getAmountMistakes(gameId)

        // assert
        verify(exactly = 1) {
            sudokuNative.getMistakes(gameId)
        }
        assertEquals(expectedAmountMistakes, amountMistakes)
    }

    @Test
    fun `isVictory() calls SudokuNative isVictory() and returns boolean`() {
        // arrange
        val gameId = 100L
        val expectedResult = false
        every { sudokuNative.isVictory(gameId) } returns expectedResult

        // act
        val result = repository.isVictory(gameId)

        // assert
        verify(exactly = 1) {
            sudokuNative.isVictory(gameId)
        }
        assertEquals(expectedResult, result)
    }

    @Test
    fun `getPuzzleField calls SudokuNative getPuzzleField() and returns GameField object`() {
        // arrange
        val gameId = 1L
        val expectedGameField = GameField(
            List(9) { row ->
                List(9) { col ->
                    GameCell(row, col, 1)
                }
            }
        )
        val intArray = IntArray(81) { 1 }
        every {
            sudokuNative.getPuzzleField(gameId)
        } returns intArray

        // act
        val gameField = repository.getPuzzleField(gameId)

        // assert
        verify(exactly = 1) {
            sudokuNative.getPuzzleField(gameId)
        }
        assert(expectedGameField == gameField)
    }
}