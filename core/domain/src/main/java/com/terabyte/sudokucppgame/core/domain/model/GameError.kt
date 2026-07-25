package com.terabyte.sudokucppgame.core.domain.model

sealed class GameError {
    data object InvalidTurn : GameError()

    data object GameNotFound : GameError()

    data object GameCellAlreadyFilled : GameError()
}