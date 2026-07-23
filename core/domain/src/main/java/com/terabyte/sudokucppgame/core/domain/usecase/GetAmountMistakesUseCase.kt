package com.terabyte.sudokucppgame.core.domain.usecase

import com.terabyte.sudokucppgame.core.domain.repository.SudokuRepository

class GetAmountMistakesUseCase(private val repository: SudokuRepository) {
    operator fun invoke(gameId: Long): Int {
        return repository.getAmountMistakes(gameId)
    }
}
