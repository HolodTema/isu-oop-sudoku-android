package usecase

import com.terabyte.sudokucppgame.core.domain.repository.SudokuRepository
import com.terabyte.sudokucppgame.core.domain.usecase.IsVictoryUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Test

class IsVictoryUseCaseTest {

    @Test
    fun `invoke() calls repository isVictory()`() {
        // arrange
        val repository: SudokuRepository = mockk()
        val gameId = 123L
        val expectedResult = true
        every {
            repository.isVictory(gameId)
        } returns expectedResult
        val useCase = IsVictoryUseCase(repository)

        // act
        val result = useCase(gameId)

        // assert
        verify(exactly = 1) {
            repository.isVictory(gameId)
        }
        assertEquals(result, expectedResult)
    }
}