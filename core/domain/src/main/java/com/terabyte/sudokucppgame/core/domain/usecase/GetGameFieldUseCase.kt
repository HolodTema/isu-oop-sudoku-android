package com.terabyte.sudokucppgame.core.domain.usecase

import com.terabyte.sudokucppgame.core.domain.model.GameField
import com.terabyte.sudokucppgame.core.domain.repository.SudokuRepository
import javax.inject.Inject

class GetGameFieldUseCase @Inject constructor(
    private val repository: SudokuRepository
) {

    operator fun invoke(gameId: Long): GameField {
        return repository.getPuzzleField(gameId)
    }

}
