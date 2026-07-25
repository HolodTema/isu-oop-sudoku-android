package com.terabyte.sudokucppgame.feature.mainmenu.intent

import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty

sealed class MainMenuIntent {
    data class OnDifficultySelectedIntent(val difficulty: GameDifficulty) : MainMenuIntent()

    data object OnButtonPlayClickedIntent : MainMenuIntent()
}