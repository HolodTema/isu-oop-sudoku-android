package com.terabyte.sudokucppgame.feature.game.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.terabyte.panopticum.core.ui.component.button.AppIconButton
import com.terabyte.panopticum.core.ui.component.button.PrimaryButton
import com.terabyte.panopticum.core.ui.component.text.MediumBodyText
import com.terabyte.panopticum.core.ui.component.text.MediumTitleText
import com.terabyte.panopticum.core.ui.icon.AppIcons
import com.terabyte.panopticum.core.ui.modifier.scroll.horizontalScrollBar
import com.terabyte.panopticum.core.ui.theme.Dimen
import com.terabyte.sudokucppgame.core.domain.model.GameCell
import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty
import com.terabyte.sudokucppgame.core.domain.model.GameField
import com.terabyte.sudokucppgame.feature.game.effect.GameEffect
import com.terabyte.sudokucppgame.feature.game.intent.GameIntent
import com.terabyte.sudokucppgame.feature.game.screen.GameCellBox
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

        GameGrid(
            chosenRow = state.chosenRow,
            chosenColumn = state.chosenColumn,
            gameField = state.field
        ) { gameCell ->
            viewModel.handleIntent(GameIntent.OnChooseRowAndColumnIntent(gameCell.row, gameCell.column))
        }

        GameFooter { value ->
            viewModel.handleIntent(GameIntent.OnMakeTurnIntent(value))
        }
    }
}

@Composable
fun GameHeader(difficulty: GameDifficulty, amountMistakes: Int, onButtonBackClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Dimen.paddingMedium)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
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
fun GameGrid(chosenRow: Int, chosenColumn: Int, gameField: GameField, onGameCellClick: (GameCell)->Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        for (row in 0..8) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (column in 0..8) {
                    GameCellBox(
                        gameCell = gameField[row, column],
                        chosenRow = chosenRow,
                        chosenColumn = chosenColumn,
                    ) {
                        onGameCellClick(gameField[row, column])
                    }
                }
            }
        }
    }
}


@Composable
fun GameCellBox(gameCell: GameCell, chosenRow: Int, chosenColumn: Int, onClick: () -> Unit) {

    val modifierBackground = if (gameCell.row == chosenRow && gameCell.column == chosenColumn) {
        Modifier
            .background(Color.Cyan)
    } else if (gameCell.row == chosenRow || gameCell.column == chosenColumn) {
        Modifier
            .background(Color.Gray)
    } else {
        Modifier
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(34.dp)
            .border(1.dp, Color.Black)
            .clickable {
                onClick()
            }
            .then(modifierBackground)
    ) {
        MediumBodyText(
            text = if (gameCell.isEmpty()) {
                ""
            } else {
                gameCell.value.toString()
            }
        )
    }
}

@Composable
fun GameFooter(onNumberClicked: (Int)->Unit) {
    val listButtonNumbers = remember { listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    }

    val lazyRowState = rememberLazyListState()

    LazyRow(
        state = lazyRowState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Dimen.paddingSmall)
            .horizontalScrollBar(lazyRowState)
    ) {
        items(listButtonNumbers) {
            PrimaryButton(
                text = it.toString(),
                onClick = {
                    onNumberClicked(it)
                }
            )
            if (it != listButtonNumbers.last()) {
                Spacer(
                    modifier = Modifier
                        .width(Dimen.paddingLarge)
                )
            }
        }
    }
}
