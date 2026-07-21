package com.terabyte.panopticum.core.ui.component.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LargeBodyText(
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = color,
        modifier = modifier
    )
}

@Composable
fun MediumBodyText(
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = color,
        modifier = modifier
    )
}
