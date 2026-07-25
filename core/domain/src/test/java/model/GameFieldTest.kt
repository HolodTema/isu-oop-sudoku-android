package model

import com.terabyte.sudokucppgame.core.domain.model.GameField
import junit.framework.TestCase.assertEquals
import org.junit.Test

class GameFieldTest {

    @Test(expected = IllegalArgumentException::class)
    fun `GameField create() throws error if int array len is not 81`() {
        // arrange
        val intArray = IntArray(30) { 0 }

        // act
        GameField.create(intArray)
    }

    @Test
    fun `GameField create() valid GameField object from valid int array`() {
        // arrange
        val array = IntArray(81) { index ->
            index % 9 + 1
        }
        // act
        val gameField = GameField.create(array)

        // assert
        assertEquals(9, gameField.listGameCells.size)
        assertEquals(9, gameField.listGameCells[0].size)

        for (row in 0..8) {
            for (column in 0..8) {
                val gameCell = gameField[row, column]
                val expectedValue = array[row * 9 + column]
                if (gameCell.value == null && expectedValue == 0) {
                    return
                }
                assertEquals(expectedValue, gameCell.value)
            }
        }
    }
}
