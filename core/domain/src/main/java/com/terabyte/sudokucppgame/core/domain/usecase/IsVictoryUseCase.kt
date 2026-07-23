package com.terabyte.sudokucppgame.core.domain.usecase

import com.terabyte.sudokucppgame.core.domain.repository.SudokuRepository

class IsVictoryUseCase(
    private val repository: SudokuRepository
) {
    operator fun invoke(gameId: Long): Boolean {
        return repository.isVictory(gameId)
    }
}
