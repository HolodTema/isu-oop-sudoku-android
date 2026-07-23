package com.terabyte.sudokucppgame.feature.mainmenu.effect

import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty

sealed class MainMenuEffect {
    data class NavigateToGameEffect(
        val difficulty: GameDifficulty
    ): MainMenuEffect()
}