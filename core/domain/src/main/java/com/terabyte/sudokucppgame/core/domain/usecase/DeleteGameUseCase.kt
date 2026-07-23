package com.terabyte.sudokucppgame.core.domain.usecase

import com.terabyte.sudokucppgame.core.domain.repository.SudokuRepository

class DeleteGameUseCase(
    private val repository: SudokuRepository
) {

    operator fun invoke(gameId: Long) {
        repository.deleteGame(gameId)
    }

}
