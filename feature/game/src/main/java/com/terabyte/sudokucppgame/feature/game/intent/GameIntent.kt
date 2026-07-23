package com.terabyte.sudokucppgame.feature.game.intent

sealed class GameIntent {
    data object OnBackToMainMenuIntent : GameIntent()

    data class OnMakeTurnIntent(
        val row: Int,
        val column: Int,
        val value: Int
    ) : GameIntent()
}