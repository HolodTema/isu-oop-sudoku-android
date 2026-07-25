package com.terabyte.sudokucppgame.activity

import VictoryScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.terabyte.panopticum.core.ui.theme.AppTheme
import com.terabyte.sudokucppgame.core.domain.model.GameDifficulty
import com.terabyte.sudokucppgame.feature.game.screen.GameScreen
import com.terabyte.sudokucppgame.feature.mainmenu.screen.MainMenuScreen
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "mainMenu"
                ) {
                    composable("mainMenu") {
                        MainMenuScreen(navController)
                    }
                    composable(
                        route = "game/{difficulty}",
                        arguments = listOf(
                            navArgument("difficulty") {
                                type = NavType.StringType
                            }
                        )
                    ) { backStackEntry ->
                        val strDifficulty = backStackEntry.arguments?.getString("difficulty")
                        val difficulty = if (strDifficulty == null) {
                            GameDifficulty.EASY
                        } else {
                            GameDifficulty.valueOf(strDifficulty)
                        }
                        GameScreen(difficulty, navController)
                    }
                    composable(
                        route = "victory/{difficulty}/{amountMistakes}",
                        arguments = listOf(
                            navArgument("difficulty") {
                                type = NavType.StringType
                            },
                            navArgument("amountMistakes") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        val strDifficulty = backStackEntry.arguments?.getString("difficulty")
                        val difficulty = if (strDifficulty == null) {
                            GameDifficulty.EASY
                        } else {
                            GameDifficulty.valueOf(strDifficulty)
                        }
                        val amountMistakes = backStackEntry.arguments?.getInt("amountMistakes") ?: 0
                        VictoryScreen(difficulty, amountMistakes, navController)
                    }
                }
            }
        }
    }
}
