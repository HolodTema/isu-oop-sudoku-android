package com.terabyte.sudokucppgame.core.domain.usecase

import com.terabyte.sudokucppgame.core.domain.repository.SudokuRepository
import javax.inject.Inject

class GetAmountMistakesUseCase @Inject constructor(
    private val repository: SudokuRepository
) {
    operator fun invoke(gameId: Long): Int {
        return repository.getAmountMistakes(gameId)
    }
}
