package com.terabyte.sudokucppgame.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.terabyte.sudokucppgame.core.data.SudokuNative
import com.terabyte.sudokucppgame.ui.theme.SudokuCppGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SudokuCppGameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SudokuScreen()
                }
            }
        }
    }
}

@Composable
fun SudokuScreen() {
    val sudokuNative = remember { SudokuNative() }
    val gamePtr = remember { sudokuNative.createGame(25) }
    val gameField = remember {sudokuNative.getPuzzleField(gamePtr) }

    Column {
        Text(
            text = "Sudoku puzzle"
        )

        gameField?.let {
            Text(text = it.joinToString(", "))
        }
    }
}