package com.terabyte.sudokucppgame.core.data.jni

class SudokuNativeImpl : SudokuNative {
    init {
        System.loadLibrary("sudoku_jni")
    }

    external override fun createGame(difficulty: Int): Long

    external override fun makeTurn(ptr: Long, row: Int, column: Int, value: Int): Boolean

    external override fun getMistakes(ptr: Long): Int

    external override fun isVictory(ptr: Long): Boolean

    external override fun getPuzzleField(ptr: Long): IntArray

    external override fun deleteGame(ptr: Long)
}