package com.terabyte.sudokucppgame.feature.mainmenu.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.terabyte.panopticum.core.ui.component.text.LargeDisplayText
import com.terabyte.panopticum.core.ui.theme.Dimen
import com.terabyte.sudokucppgame.feature.mainmenu.effect.MainMenuEffect
import com.terabyte.sudokucppgame.feature.mainmenu.viewmodel.MainMenuViewModel

@Composable
fun MainMenuScreen(
    navController: NavController,
    viewModel: MainMenuViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MainMenuEffect.NavigateToGameEffect -> {
                    navController.navigate("g   ame")
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
        LargeDisplayText(text = "Sudoku")
    }
}