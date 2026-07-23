package com.terabyte.sudokucppgame.feature.mainmenu.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.terabyte.panopticum.core.ui.component.button.AppButton
import com.terabyte.panopticum.core.ui.component.button.PrimaryButton
import com.terabyte.panopticum.core.ui.component.text.LargeDisplayText
import com.terabyte.panopticum.core.ui.component.text.MediumTitleText
import com.terabyte.panopticum.core.ui.theme.Dimen
import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty
import com.terabyte.sudokucppgame.feature.mainmenu.effect.MainMenuEffect
import com.terabyte.sudokucppgame.feature.mainmenu.intent.MainMenuIntent
import com.terabyte.sudokucppgame.feature.mainmenu.viewmodel.MainMenuViewModel

@Composable
fun MainMenuScreen(
    navController: NavController,
    viewModel: MainMenuViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MainMenuEffect.NavigateToGameEffect -> {
                    navController.navigate("game/${effect.difficulty.name}")
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
        LargeDisplayText(
            text = "Sudoku",
            modifier = Modifier
                .padding(bottom = Dimen.paddingMedium)
        )


        val modifierSelectedDifficultyBorder = Modifier
            .border(2.dp, Color.Red)
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            MediumTitleText(
                text = "Choose difficulty level:",
                modifier = Modifier
                    .padding(bottom = Dimen.paddingMedium)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AppButton(
                    text = "Easy",
                    onClick = {
                        viewModel.handleIntent(
                            MainMenuIntent.OnDifficultySelectedIntent(
                                GameDifficulty.EASY
                            )
                        )
                    },
                    color = if (state.difficulty == GameDifficulty.EASY) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary
                    }
                )
                AppButton(
                    text = "Medium",
                    onClick = {
                        viewModel.handleIntent(
                            MainMenuIntent.OnDifficultySelectedIntent(
                                GameDifficulty.MEDIUM
                            )
                        )
                    },
                    color = if (state.difficulty == GameDifficulty.MEDIUM) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary
                    }
                )
                AppButton(
                    text = "Hard",
                    onClick = {
                        viewModel.handleIntent(
                            MainMenuIntent.OnDifficultySelectedIntent(
                                GameDifficulty.HARD
                            )
                        )
                    },
                    color = if (state.difficulty == GameDifficulty.HARD) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary
                    }
                )
            }
        }

        PrimaryButton(
            text = "Play",
            onClick = {
                viewModel.handleIntent(MainMenuIntent.OnButtonPlayClickedIntent)
            }
        )
    }
}