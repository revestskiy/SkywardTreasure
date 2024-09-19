package com.SkyFlyGame.SkywardTreasure

import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val isWin: Boolean,
    val level: Int,
    val score: Int,
    val targetScore: Int,
    val timer: Int
) : Parcelable

@Composable
fun LevelCompleteScreen(
    isWin: Boolean,
    level: Int,
    score: Int,
    targetScore: Int,
    timer: Int,
    onMenu: () -> Unit,
    onBack: () -> Unit
) {
    // Main background with image
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background), // Replace with your background image
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Foreground container with timer, score and buttons
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFB3E5FC),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextWithShadowAndOutline(
                        text = if (isWin) "Level complete" else "Level failed",
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        modifier = Modifier
                            .background(
                                color = Color(0xFF0288D1),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextWithShadowAndOutline(
                        text = "Timer: ${timer.millisFormatted}s",
                        fontSize = 24.sp,
                    )
                    TextWithShadowAndOutline(
                        text = "Score: $score/$targetScore",
                        fontSize = 24.sp,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onBack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB3E5FC))
            ) {
                TextWithShadowAndOutline(
                    text = if (isWin) "Next" else "Retry",
                    fontSize = 20.sp,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onMenu() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB3E5FC))
            ) {
                Text(text = "Menu", fontSize = 20.sp, color = Color.Black)
            }
        }
    }
}