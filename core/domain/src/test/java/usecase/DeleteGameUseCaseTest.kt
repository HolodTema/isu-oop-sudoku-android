package usecase

import com.terabyte.sudokucppgame.core.domain.repository.SudokuRepository
import com.terabyte.sudokucppgame.core.domain.usecase.DeleteGameUseCase
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class DeleteGameUseCaseTest {

    @Test
    fun `invoke() calls repository deleteGame()`() {
        // arrange
        val repository: SudokuRepository = mockk()
        every { repository.deleteGame(any()) } just Runs
        val gameId = 123L
        val useCase = DeleteGameUseCase(repository)

        //act
        useCase(gameId)

        // assert
        verify(exactly = 1) {
            repository.deleteGame(gameId)
        }
    }
}