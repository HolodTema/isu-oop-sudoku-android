package com.terabyte.sudokucppgame.feature.victory.viewmodel

import VictoryEffect
import VictoryIntent
import androidx.compose.ui.input.key.Key.Companion.D
import androidx.lifecycle.SavedStateHandle
import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
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
        val deferredEffect = async(UnconfinedTestDispatcher()) {
            viewModel.effect.first()
        }
        viewModel.handleIntent(intent)
        val effect = deferredEffect.await()

        // assert
        assert(effect is VictoryEffect.OnNavigateToMainMenu)
    }
}