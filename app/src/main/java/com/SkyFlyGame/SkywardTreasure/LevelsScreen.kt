package com.SkyFlyGame.SkywardTreasure

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun LevelsScreen(
    onLevelChosen: (Int) -> Unit
) {
    LaunchedEffect(Unit) {
        onLevelChosen(1)
    }
}