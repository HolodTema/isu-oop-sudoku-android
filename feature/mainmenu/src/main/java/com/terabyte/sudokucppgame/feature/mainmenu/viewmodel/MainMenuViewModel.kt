package com.terabyte.sudokucppgame.feature.mainmenu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty
import com.terabyte.sudokucppgame.feature.mainmenu.effect.MainMenuEffect
import com.terabyte.sudokucppgame.feature.mainmenu.intent.MainMenuIntent
import com.terabyte.sudokucppgame.feature.mainmenu.state.MainMenuState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(MainMenuState(GameDifficulty.EASY))
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<MainMenuEffect>()
    val effect = _effect.asSharedFlow()

    fun handleIntent(intent: MainMenuIntent) {
        when (intent) {
            is MainMenuIntent.OnDifficultySelectedIntent -> {
                _state.update {
                    it.copy(
                        difficulty = intent.difficulty
                    )
                }
            }
            is MainMenuIntent.OnButtonPlayClickedIntent -> {
                val currentDifficulty = state.value.difficulty
                viewModelScope.launch {
                    _effect.emit(MainMenuEffect.NavigateToGameEffect(currentDifficulty))
                }
            }
        }
    }
}