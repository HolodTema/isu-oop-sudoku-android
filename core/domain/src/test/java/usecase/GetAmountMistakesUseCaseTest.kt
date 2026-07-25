package usecase

import com.terabyte.sudokucppgame.core.domain.repository.SudokuRepository
import com.terabyte.sudokucppgame.core.domain.usecase.GetAmountMistakesUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Test

class GetAmountMistakesUseCaseTest {

    @Test
    fun `invoke() calls repository getAmountMistakes()`() {
        // arrange
        val repository: SudokuRepository = mockk()
        val gameId = 123L
        val expectedResult = 5
        every { repository.getAmountMistakes(gameId) } returns expectedResult
        val useCase = GetAmountMistakesUseCase(repository)

        // act
        val result = useCase(gameId)

        // assert
        verify(exactly = 1) {
            repository.getAmountMistakes(gameId)
        }
        assertEquals(expectedResult, result)
    }

}
