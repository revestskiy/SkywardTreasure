package com.SkyFlyGame.SkywardTreasure

import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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


@Preview
@Composable
fun LevelCompleteScreen(
    isWin: Boolean = true,
    level: Int = 1,
    score: Int = 50,
    targetScore: Int = 50,
    timer: Int = 20,
    onMenu: () -> Unit = {},
    onBack: () -> Unit = {},
    onRetry: () -> Unit = {},
    onNext: () -> Unit = {}
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
                    .fillMaxWidth(0.8f)
                    .background(
                        color = Color(0xFFD6FAFF),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .border(
                        width = 5.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(10.dp)
                    )

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextWithShadowAndOutline(
                        text = if (isWin) "Level complete" else "Level failed",
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFFD6FAFF),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 5.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextWithShadowAndOutline(
                        text = "Timer: ${timer.millisFormatted}s",
                        fontSize = 32.sp,
                    )
                    TextWithShadowAndOutline(
                        text = "Score: $score/$targetScore",
                        fontSize = 32.sp,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(id = if (isWin) R.drawable.nextbutton else R.drawable.retrybutton),
                contentDescription = if (isWin) "Next Button" else "Retry Button",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickableWithoutRipple {
                        if (isWin) {
                            onNext()
                        } else {
                            onRetry()
                        }
                    }
            )


            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.menubutton),
                contentDescription = "Menu Button",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickableWithoutRipple {
                        onMenu()
                    }
            )
        }
    }
}
@Composable
fun Modifier.clickableWithoutRipple(
    onClick: () -> Unit
) = then(
    Modifier.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
    )
)