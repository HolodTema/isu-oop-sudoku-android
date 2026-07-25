package com.terabyte.sudokucppgame.core.domain.usecase

import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty
import com.terabyte.sudokucppgame.core.domain.repository.SudokuRepository
import javax.inject.Inject

class CreateGameUseCase @Inject constructor(
    private val repository: SudokuRepository
) {

    operator fun invoke(difficulty: GameDifficulty): Long {
        return repository.createGame(difficulty)
    }

}
