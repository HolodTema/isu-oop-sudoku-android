package com.terabyte.sudokucppgame.feature.game.effect

import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty

sealed class GameEffect {

    data object OnShowToastMistakeEffect : GameEffect()

    data object OnShowToastRightTurnEffect : GameEffect()

    data object OnNavigateToMainMenuEffect : GameEffect()

    data class OnNavigateToVictoryEffect(
        val difficulty: GameDifficulty,
        val amountMistakes: Int
    ) : GameEffect()
}