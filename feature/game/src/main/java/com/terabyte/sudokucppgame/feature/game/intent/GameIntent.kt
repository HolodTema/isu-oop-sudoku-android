package com.terabyte.sudokucppgame.feature.game.intent

sealed class GameIntent {
    data object OnBackToMainMenuIntent : GameIntent()

    data class OnMakeTurnIntent(
        val value: Int
    ) : GameIntent()

    data class OnChooseRowAndColumnIntent(
        val row: Int,
        val column: Int
    ) : GameIntent()
}