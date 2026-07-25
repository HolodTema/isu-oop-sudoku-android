package usecase

import com.terabyte.sudokucppgame.core.domain.model.GameCell
import com.terabyte.sudokucppgame.core.domain.model.GameField
import com.terabyte.sudokucppgame.core.domain.repository.SudokuRepository
import com.terabyte.sudokucppgame.core.domain.usecase.GetGameFieldUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Test

class GetGameFieldUseCase {

    @Test
    fun `invoke() calls repository getPuzzleField()`() {
        // arrange
        val repository: SudokuRepository = mockk()
        val gameId = 123L
        val listGameCells = listOf(
            listOf(
                GameCell(0, 0, 3),
                GameCell(0, 0, 3),
                GameCell(0, 0, 3),
            ),
            listOf(
                GameCell(0, 0, 3),
                GameCell(0, 0, 3),
                GameCell(0, 0, 3),
            )
        )
        val expectedGameField = GameField(listGameCells)
        every { repository.getPuzzleField(gameId) } returns expectedGameField
        val useCase = GetGameFieldUseCase(repository)

        // act
        val gameField = useCase(gameId)

        // assert
        verify(exactly = 1) {
            repository.getPuzzleField(gameId)
        }
        assertEquals(expectedGameField, gameField)
    }
}