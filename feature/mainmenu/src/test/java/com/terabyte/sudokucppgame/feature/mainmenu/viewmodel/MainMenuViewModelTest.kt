package com.terabyte.sudokucppgame.feature.mainmenu.viewmodel

import androidx.lifecycle.viewmodel.compose.viewModel
import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty
import com.terabyte.sudokucppgame.feature.mainmenu.effect.MainMenuEffect
import com.terabyte.sudokucppgame.feature.mainmenu.intent.MainMenuIntent
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.Test

class MainMenuViewModelTest {
    private val viewModel = MainMenuViewModel()

    @Test
    fun `selecting difficulty updates MainMenuState`() {
        // arrange
        val intent = MainMenuIntent.OnDifficultySelectedIntent(GameDifficulty.MEDIUM)

        // act
        viewModel.handleIntent(intent)

        // assert
        assertEquals(GameDifficulty.MEDIUM, viewModel.state.value.difficulty)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `OnButtonPlayClickedIntent handling emits NavigateToGameEffect`() = runTest {
        // arrange
        val chosenDifficulty = GameDifficulty.MEDIUM
        val onButtonPlayClickedIntent = MainMenuIntent.OnButtonPlayClickedIntent
        val onDifficultySelectedIntent = MainMenuIntent.OnDifficultySelectedIntent(chosenDifficulty)
        viewModel.handleIntent(onDifficultySelectedIntent)

        // act
        val deferredEffect = async(UnconfinedTestDispatcher()) {
            viewModel.effect.first()
        }
        viewModel.handleIntent(onButtonPlayClickedIntent)
        val effect = deferredEffect.await()

        // assert
        assert(effect is MainMenuEffect.NavigateToGameEffect)
        assertEquals(chosenDifficulty, (effect as MainMenuEffect.NavigateToGameEffect).difficulty)
    }
}
