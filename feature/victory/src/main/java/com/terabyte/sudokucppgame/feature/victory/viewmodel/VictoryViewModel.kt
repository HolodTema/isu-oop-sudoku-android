package com.terabyte.sudokucppgame.feature.victory.viewmodel

import VictoryEffect
import VictoryIntent
import VictoryState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VictoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state: MutableStateFlow<VictoryState> = run {
        val strDifficulty = savedStateHandle.get<String>("difficulty") ?: "EASY"
        val difficulty = GameDifficulty.valueOf(strDifficulty)
        val amountMistakes = savedStateHandle.get<Int>("amountMistakes") ?: 0
        val state = VictoryState(
            difficulty = difficulty,
            amountMistakes = amountMistakes
        )
        MutableStateFlow(state)
    }
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<VictoryEffect>()
    val effect = _effect.asSharedFlow()

    fun handleIntent(intent: VictoryIntent) {
        when (intent) {
            VictoryIntent.OnBackToMainMenuIntent -> {
                viewModelScope.launch {
                    _effect.emit(VictoryEffect.OnNavigateToMainMenu)
                }
            }
        }
    }
}