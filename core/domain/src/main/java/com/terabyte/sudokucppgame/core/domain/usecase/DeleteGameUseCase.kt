package com.terabyte.sudokucppgame.core.domain.usecase

import com.terabyte.sudokucppgame.core.domain.repository.SudokuRepository
import javax.inject.Inject

class DeleteGameUseCase @Inject constructor(
    private val repository: SudokuRepository
) {

    operator fun invoke(gameId: Long) {
        repository.deleteGame(gameId)
    }

}
