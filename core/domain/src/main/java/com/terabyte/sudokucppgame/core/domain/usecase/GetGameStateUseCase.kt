package com.terabyte.sudokucppgame.core.domain.usecase

import com.terabyte.sudokucppgame.core.domain.model.GameState

class GetGameStateUseCase(
    private val getGameFieldUseCase: GetGameFieldUseCase,
    private val getAmountMistakesUseCase: GetAmountMistakesUseCase,
    private val isVictoryUseCase: IsVictoryUseCase
) {

    operator fun invoke(gameId: Long): GameState {
        return GameState(
            field = getGameFieldUseCase(gameId),
            amountMistakes = getAmountMistakesUseCase(gameId),
            isVictory = isVictoryUseCase(gameId)
        )
    }

}
