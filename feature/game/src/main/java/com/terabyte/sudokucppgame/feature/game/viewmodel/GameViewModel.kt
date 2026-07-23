package com.terabyte.sudokucppgame.feature.game.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty
import com.terabyte.sudokucppgame.core.domain.usecase.CreateGameUseCase
import com.terabyte.sudokucppgame.core.domain.usecase.GetAmountMistakesUseCase
import com.terabyte.sudokucppgame.core.domain.usecase.GetGameFieldUseCase
import com.terabyte.sudokucppgame.core.domain.usecase.IsVictoryUseCase
import com.terabyte.sudokucppgame.core.domain.usecase.MakeTurnUseCase
import com.terabyte.sudokucppgame.feature.game.effect.GameEffect
import com.terabyte.sudokucppgame.feature.game.effect.GameEffect.OnNavigateToVictoryEffect
import com.terabyte.sudokucppgame.feature.game.intent.GameIntent
import com.terabyte.sudokucppgame.feature.game.state.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val createGameUseCase: CreateGameUseCase,
    private val getGameFieldUseCase: GetGameFieldUseCase,
    private val getAmountMistakesUseCase: GetAmountMistakesUseCase,
    private val makeTurnUseCase: MakeTurnUseCase,
    private val isVictoryUseCase: IsVictoryUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableStateFlow<GameState> = run {
        val strDifficulty = savedStateHandle.get<String>("difficulty")
        val difficulty = if (strDifficulty == null) {
            GameDifficulty.EASY
        } else {
            GameDifficulty.valueOf(strDifficulty)
        }
        val gameId = createGameUseCase(difficulty)
        val gameState = GameState(
            gameId = gameId,
            field = getGameFieldUseCase(gameId),
            difficulty = difficulty,
            amountMistakes = getAmountMistakesUseCase(gameId),
            chosenRow = 0,
            chosenColumn = 0

        )
        MutableStateFlow(gameState)
    }
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<GameEffect>()
    val effect = _effect.asSharedFlow()

    fun handleIntent(intent: GameIntent) {
        when (intent) {
            GameIntent.OnBackToMainMenuIntent -> {
                viewModelScope.launch {
                    _effect.emit(GameEffect.OnNavigateToMainMenuEffect)
                }
            }

            is GameIntent.OnMakeTurnIntent -> {
                val isRightTurn =
                    makeTurnUseCase(state.value.gameId, intent.row, intent.column, intent.value)
                updateGameState()
                if (isRightTurn) {
                    if (isVictoryUseCase(state.value.gameId)) {
                        val difficulty = state.value.difficulty
                        val amountMistakes = state.value.amountMistakes
                        viewModelScope.launch {
                            _effect.emit(
                                OnNavigateToVictoryEffect(
                                    difficulty,
                                    amountMistakes
                                )
                            )
                        }
                    } else {
                        viewModelScope.launch {
                            _effect.emit(GameEffect.OnShowToastRightTurnEffect)
                        }
                    }
                } else {
                    viewModelScope.launch {
                        _effect.emit(GameEffect.OnShowToastMistakeEffect)
                    }
                }
            }

            is GameIntent.OnChooseRowAndColumnIntent -> {
                _state.update {
                    it.copy(
                        chosenRow = intent.row,
                        chosenColumn = intent.column
                    )
                }
            }
        }
    }

    private fun updateGameState() {
        _state.update {
            it.copy(
                gameId = it.gameId,
                field = getGameFieldUseCase(it.gameId),
                amountMistakes = getAmountMistakesUseCase(it.gameId)
            )
        }
    }

}