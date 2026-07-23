package com.terabyte.sudokucppgame.feature.game.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.terabyte.panopticum.core.ui.component.button.AppIconButton
import com.terabyte.panopticum.core.ui.component.text.MediumBodyText
import com.terabyte.panopticum.core.ui.component.text.MediumTitleText
import com.terabyte.panopticum.core.ui.icon.AppIcons
import com.terabyte.panopticum.core.ui.theme.Dimen
import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty
import com.terabyte.sudokucppgame.feature.game.effect.GameEffect
import com.terabyte.sudokucppgame.feature.game.intent.GameIntent
import com.terabyte.sudokucppgame.feature.game.viewmodel.GameViewModel

@Composable
fun GameScreen(
    difficulty: GameDifficulty,
    navController: NavController,
    viewModel: GameViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                GameEffect.OnNavigateToMainMenuEffect -> {
                    navController.navigate("mainMenu")
                }
                is GameEffect.OnNavigateToVictoryEffect -> {
                    navController.navigate("victory/${state.difficulty}/${state.amountMistakes}")
                }
                is GameEffect.OnShowToastMistakeEffect -> {
                    Toast.makeText(context, "Mistake!", Toast.LENGTH_SHORT).show()
                }
                is GameEffect.OnShowToastRightTurnEffect -> {
                    Toast.makeText(context, "Right!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimen.paddingLarge)
    ) {
        GameHeader(
            difficulty = state.difficulty,
            amountMistakes = state.amountMistakes
        ) {
            viewModel.handleIntent(GameIntent.OnBackToMainMenuIntent)
        }

        GameGrid()

        GameFooter()
    }
}

@Composable
fun GameHeader(difficulty: GameDifficulty, amountMistakes: Int, onButtonBackClicked: ()->Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Dimen.paddingMedium)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AppIconButton(AppIcons.Back, onButtonBackClicked)
            MediumTitleText(
                text = "Sudoku: ${difficulty.name} difficulty",
                modifier = Modifier
                    .weight(1f)
            )
        }
        MediumBodyText(
            text = "Mistakes: ${amountMistakes}"
        )
    }
}

@Composable
fun GameGrid() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

        }
    }
}

@Composable
fun GameFooter() {

}
