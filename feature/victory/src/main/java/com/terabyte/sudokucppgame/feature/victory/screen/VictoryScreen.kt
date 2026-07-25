import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.terabyte.panopticum.core.ui.component.button.PrimaryButton
import com.terabyte.panopticum.core.ui.component.text.LargeBodyText
import com.terabyte.panopticum.core.ui.component.text.LargeDisplayText
import com.terabyte.panopticum.core.ui.theme.Dimen
import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty
import com.terabyte.sudokucppgame.feature.victory.viewmodel.VictoryViewModel
import timber.log.Timber


@Composable
fun VictoryScreen(
    difficulty: GameDifficulty,
    amountMistakes: Int,
    navController: NavController,
    viewModel: VictoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        Timber.d("Check out to VictoryScreen")

        viewModel.effect.collect {
            when (it) {
                VictoryEffect.OnNavigateToMainMenu -> {
                    navController.navigate("mainMenu")
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimen.paddingLarge)
    ) {
        LargeDisplayText("Victory")
        LargeBodyText("You have solved the ${state.difficulty}-level sudoku with ${amountMistakes} mistakes!")
        PrimaryButton(
            text = "Go to Main Menu",
            onClick = {
                viewModel.handleIntent(VictoryIntent.OnBackToMainMenuIntent)
            }
        )
    }

}