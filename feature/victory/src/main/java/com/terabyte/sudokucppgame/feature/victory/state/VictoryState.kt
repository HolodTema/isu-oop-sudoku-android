import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty

data class VictoryState(
    val difficulty: GameDifficulty,
    val amountMistakes: Int
)