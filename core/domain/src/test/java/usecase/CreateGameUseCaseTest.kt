package usecase

import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty
import com.terabyte.sudokucppgame.core.domain.repository.SudokuRepository
import com.terabyte.sudokucppgame.core.domain.usecase.CreateGameUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CreateGameUseCaseTest {
    private val repository: SudokuRepository = mockk()
    private val useCase = CreateGameUseCase(repository)

    @Test
    fun `invoke() calls repository createGame()`() {
        // arrange
        val difficulty = GameDifficulty.EASY
        val expectedId = 123L
        every {
            repository.createGame(difficulty)
        } returns expectedId

        // act
        val gameId = useCase.invoke(difficulty)

        // assert
        verify(exactly = 1) {
            repository.createGame(difficulty)
        }
        assertEquals(expectedId, gameId)
    }
}
