package model

import com.terabyte.sudokucppgame.core.domain.model.GameCell
import junit.framework.TestCase.assertEquals
import org.junit.Test

class GameCellTest {

    @Test
    fun `GameCell is empty only when value is null`() {
        // arrange
        val value: Int? = null

        // act
        val gameCell = GameCell(0, 0, value)

        // assert
        assertEquals(true, gameCell.isEmpty())
    }

}
