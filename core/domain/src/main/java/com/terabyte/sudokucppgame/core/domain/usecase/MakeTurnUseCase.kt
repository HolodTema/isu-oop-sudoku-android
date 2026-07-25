package com.terabyte.sudokucppgame.core.domain.usecase

import com.terabyte.sudokucppgame.core.domain.repository.SudokuRepository
import javax.inject.Inject

class MakeTurnUseCase @Inject constructor(
    private val repository: SudokuRepository
) {

    operator fun invoke(gameId: Long, row: Int, column: Int, value: Int): Boolean {
        return repository.makeTurn(gameId, row, column, value)
    }

}
