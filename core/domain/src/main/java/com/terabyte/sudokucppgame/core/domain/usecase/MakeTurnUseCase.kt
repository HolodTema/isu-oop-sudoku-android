package com.terabyte.sudokucppgame.core.domain.usecase

import com.terabyte.sudokucppgame.core.domain.repository.SudokuRepository

class MakeTurnUseCase(private val repository: SudokuRepository) {

    operator fun invoke(gameId: Long, row: Int, column: Int, value: Int): Boolean {
        return repository.makeTurn(gameId, row, column, value)
    }
}