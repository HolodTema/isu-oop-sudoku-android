package com.terabyte.sudokucppgame

class SudokuNative {
    init {
        System.loadLibrary("sudoku_jni")
    }

    external fun createGame(difficulty: Int): Long

    external fun makeTurn(ptr: Long, row: Int, column: Int, value: Int): Boolean

    external fun getMistakes(ptr: Long): Boolean

    external fun isVictory(ptr: Long): Boolean

    external fun getPuzzleField(ptr: Long): IntArray

    external fun deleteGame(ptr: Long)
}