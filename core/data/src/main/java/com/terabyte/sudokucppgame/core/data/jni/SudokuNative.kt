package com.terabyte.sudokucppgame.core.data.jni

interface SudokuNative {
    fun createGame(difficulty: Int): Long

    fun makeTurn(ptr: Long, row: Int, column: Int, value: Int): Boolean

    fun getMistakes(ptr: Long): Int

    fun isVictory(ptr: Long): Boolean

    fun getPuzzleField(ptr: Long): IntArray

    fun deleteGame(ptr: Long)
}