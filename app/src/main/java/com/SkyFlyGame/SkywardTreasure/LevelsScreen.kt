package com.SkyFlyGame.SkywardTreasure

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LevelsScreen(
    onBack: () -> Unit = {},
    onLevelSelect: (Int) -> Unit = {}
) {
    // Main background with image
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.background),  // Replace with your background image
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Back button
        Image(
            painter = painterResource(id = R.drawable.back),  // Replace with your back button image
            contentDescription = "Back",
            modifier = Modifier
                .size(60.dp)
                .padding(8.dp)
                .clickableWithoutRipple { onBack() }
                .align(Alignment.TopStart)
        )

        // Levels grid
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp, start = 16.dp, end = 16.dp)
                .verticalScroll(androidx.compose.foundation.rememberScrollState())
            ,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (row in 0 until 10) {  // 10 rows for 40 levels
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    for (col in 1..4) {  // 4 buttons in each row
                        val levelNumber = row * 4 + col

                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(width = 75.dp, height = 60.dp)
                                .alpha(if (Prefs.isLevelAvailable(levelNumber)) 1f else 0.5f)
                                .background(
                                    color = Color(0xFFD6FAFF),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(3.dp, Color.Black, RoundedCornerShape(8.dp))
                                .clickableWithoutRipple {
                                    if (Prefs.isLevelAvailable(levelNumber)) {
                                        onLevelSelect(levelNumber)
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            // Text with shadow and outline
                            TextWithShadowAndOutline(
                                text = "Lvl $levelNumber",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))  // Space between rows
            }
        }
    }
}

