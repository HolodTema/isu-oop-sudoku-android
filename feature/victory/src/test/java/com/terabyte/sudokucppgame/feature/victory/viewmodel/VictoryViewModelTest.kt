package com.terabyte.sudokucppgame.feature.victory.viewmodel

import VictoryEffect
import VictoryIntent
import androidx.compose.ui.input.key.Key.Companion.D
import androidx.lifecycle.SavedStateHandle
import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test


class VictoryViewModelTest {

    @Test
    fun `VictoryState is initialized from savedStateHandle`() {
        // arrange
        val savedStateHandle = SavedStateHandle(
            mapOf(
                "difficulty" to "MEDIUM",
                "amountMistakes" to 30
            )
        )

        // act
        val viewModel = VictoryViewModel(savedStateHandle)
        val state = viewModel.state.value

        // assert
        assertEquals("MEDIUM", state.difficulty.name)
        assertEquals(30, state.amountMistakes)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `OnBackToMainMenuIntent handling emits OnNavigateToMainMenu effect`() = runTest {
        // arrange
        val savedStateHandle = SavedStateHandle(
            mapOf(
                "difficulty" to "HARD",
                "amountMistakes" to 3
            )
        )
        val viewModel = VictoryViewModel(savedStateHandle)
        val intent = VictoryIntent.OnBackToMainMenuIntent

        // act
        val listEffects = mutableListOf<VictoryEffect>()
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.effect.collect {
                listEffects.add(it)
            }
        }
        viewModel.handleIntent(intent)
        delay(100)
        job.cancel()
        val effect = listEffects.first()

        // assert
        assert(effect is VictoryEffect.OnNavigateToMainMenu)
    }
}