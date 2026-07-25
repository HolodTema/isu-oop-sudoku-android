package usecase

import com.terabyte.sudokucppgame.core.domain.repository.SudokuRepository
import com.terabyte.sudokucppgame.core.domain.usecase.MakeTurnUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MakeTurnUseCaseTest {

    @Test
    fun `invoke() calls repository makeTurn()`() {
        // arrange
        val gameId = 123L
        val row = 1
        val column = 1
        val value = 5
        val expectedResult = true
        val repository: SudokuRepository = mockk()
        every { repository.makeTurn(gameId, row, column, value) } returns expectedResult
        val useCase = MakeTurnUseCase(repository)

        // act
        val result = useCase(gameId, row, column, value)

        // assert
        verify(exactly = 1) {
            repository.makeTurn(gameId, row, column, value)
        }
        assertEquals(result, expectedResult)
    }
}