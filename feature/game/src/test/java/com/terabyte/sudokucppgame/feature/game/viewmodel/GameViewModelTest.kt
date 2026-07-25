package com.terabyte.sudokucppgame.feature.game.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty
import com.terabyte.sudokucppgame.core.domain.model.GameField
import com.terabyte.sudokucppgame.core.domain.usecase.CreateGameUseCase
import com.terabyte.sudokucppgame.core.domain.usecase.GetAmountMistakesUseCase
import com.terabyte.sudokucppgame.core.domain.usecase.GetGameFieldUseCase
import com.terabyte.sudokucppgame.core.domain.usecase.IsVictoryUseCase
import com.terabyte.sudokucppgame.core.domain.usecase.MakeTurnUseCase
import com.terabyte.sudokucppgame.feature.game.effect.GameEffect
import com.terabyte.sudokucppgame.feature.game.intent.GameIntent
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.Test


class GameViewModelTest {
    private val createGameUseCase: CreateGameUseCase = mockk()
    private val getGameFieldUseCase: GetGameFieldUseCase = mockk()
    private val getAmountMistakesUseCase: GetAmountMistakesUseCase = mockk()
    private val makeTurnUseCase: MakeTurnUseCase = mockk()
    private val isVictoryUseCase: IsVictoryUseCase = mockk()

    private fun createGameViewModel(difficulty: GameDifficulty): GameViewModel {
        val savedStateHandle = SavedStateHandle(
            mapOf("difficulty" to difficulty.name)
        )
        val gameId = 1L
        val gameField = GameField.create(IntArray(81) { 0 })

        every { createGameUseCase(difficulty) } returns gameId
        every { getGameFieldUseCase(gameId) } returns gameField
        every { getAmountMistakesUseCase(gameId) } returns 0

        return GameViewModel(
            createGameUseCase,
            getGameFieldUseCase,
            getAmountMistakesUseCase,
            makeTurnUseCase,
            isVictoryUseCase,
            savedStateHandle
        )
    }

    @Test
    fun `initial state of GameState is correct`() {
        // act
        val viewModel = createGameViewModel(GameDifficulty.HARD)
        val state = viewModel.state.value

        // assert
        assertEquals(0, state.chosenRow)
        assertEquals(0, state.chosenColumn)
        assertEquals(0, state.amountMistakes)
        assertEquals("HARD", state.difficulty.name)
        assertNotNull(state.field)
    }

    @Test
    fun `GameState is initialized from savedStateHandle`() {
        // act
        val viewModel = createGameViewModel(GameDifficulty.HARD)
        val state = viewModel.state.value

        // assert
        assertEquals("HARD", state.difficulty.name)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `OnBackToMainMenuIntent handling emits OnNavigateToMainMenuEffect`() = runTest {
        // arrange
        val viewModel = createGameViewModel(GameDifficulty.MEDIUM)
        val intent = GameIntent.OnBackToMainMenuIntent

        // act
        val deferredEffect = async(UnconfinedTestDispatcher()) {
            viewModel.effect.first()
        }
        viewModel.handleIntent(intent)
        val effect = deferredEffect.await()

        // assert
        assert(effect is GameEffect.OnNavigateToMainMenuEffect)
    }

    @Test
    fun `OnChooseRowAndColumnIntent handling updates GameState`() {
        // arrange
        val expectedRow = 3
        val expectedColumn = 5
        val viewModel = createGameViewModel(GameDifficulty.MEDIUM)
        val intent = GameIntent.OnChooseRowAndColumnIntent(
            row = expectedRow,
            column = expectedColumn
        )

        // act
        viewModel.handleIntent(intent)
        val state = viewModel.state.value

        // assert
        assertEquals(expectedRow, state.chosenRow)
        assertEquals(expectedColumn, state.chosenColumn)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `OnMakeTurnIntent with right turn and victory handling emits OnNavigateToVictoryEffect`() = runTest {
        // arrange
        val viewModel = createGameViewModel(GameDifficulty.MEDIUM)
        viewModel.handleIntent(GameIntent.OnChooseRowAndColumnIntent(0, 0))
        val gameId = viewModel.state.value.gameId
        every { makeTurnUseCase(gameId, 0, 0, 5) } returns true
        every { isVictoryUseCase(gameId) } returns true

        // act
        val deferredEffect: Deferred<GameEffect> = async(UnconfinedTestDispatcher()) {
            viewModel.effect.first()
        }
        viewModel.handleIntent(GameIntent.OnMakeTurnIntent(5))
        val effect = deferredEffect.await()

        // assert
        assert(effect is GameEffect.OnNavigateToVictoryEffect)
        if (effect is GameEffect.OnNavigateToVictoryEffect) {
            assertEquals(GameDifficulty.MEDIUM, effect.difficulty)
            assertEquals(viewModel.state.value.amountMistakes, effect.amountMistakes)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `OnMakeTurnIntent with right turn but without victory handling emits OnShowToastRightTurnEffect`() = runTest {
        // arrange
        val viewModel = createGameViewModel(GameDifficulty.MEDIUM)
        viewModel.handleIntent(GameIntent.OnChooseRowAndColumnIntent(0, 0))
        val gameId = viewModel.state.value.gameId
        every { makeTurnUseCase(gameId, 0, 0, 5) } returns true
        every { isVictoryUseCase(gameId) } returns false

        // act
        val deferredEffect = async(UnconfinedTestDispatcher()) {
            viewModel.effect.first()
        }
        viewModel.handleIntent(GameIntent.OnMakeTurnIntent(5))
        val effect = deferredEffect.await()

        // assert
        assert(effect is GameEffect.OnShowToastRightTurnEffect)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `OnMakeTurnIntent with mistake turn handling emits OnShowToastMistakeEffect`() = runTest {
        // arrange
        val viewModel = createGameViewModel(GameDifficulty.MEDIUM)
        viewModel.handleIntent(GameIntent.OnChooseRowAndColumnIntent(0, 0))
        val gameId = viewModel.state.value.gameId
        every { makeTurnUseCase(gameId, 0, 0, 5) } returns false

        // act
        val deferredEffect = async(UnconfinedTestDispatcher()) {
            viewModel.effect.first()
        }
        viewModel.handleIntent(GameIntent.OnMakeTurnIntent(5))
        val effect = deferredEffect.await()

        // assert
        assert(effect is GameEffect.OnShowToastMistakeEffect)
    }
}