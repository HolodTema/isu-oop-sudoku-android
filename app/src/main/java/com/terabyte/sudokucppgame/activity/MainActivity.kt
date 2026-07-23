package com.terabyte.sudokucppgame.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.terabyte.panopticum.core.ui.theme.AppTheme
import com.terabyte.sudokucppgame.feature.mainmenu.screen.MainMenuScreen
import dagger.hilt.android.AndroidEntryPoint

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
                    composable("game") {
//                        GameScreen()
                    }
                    composable("victory") {
//                        VictoryScreen()
                    }
                }
            }
        }
    }
}
