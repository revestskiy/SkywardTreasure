package com.SkyFlyGame.SkywardTreasure

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.SkyFlyGame.SkywardTreasure.ui.theme.nujnoefont

@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    var musicVolume by remember { mutableFloatStateOf(Prefs.musicVolume) }
    var soundVolume by remember { mutableFloatStateOf(Prefs.soundVolume) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(id = R.drawable.settingbackground),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(300.dp)

        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(32.dp)
        ) {


            TextWithShadowAndOutline(
                text = "Settings",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))


            TextWithShadowAndOutline(
                text = "Music",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Slider(
                value = musicVolume,
                onValueChange = {
                    musicVolume = it
                    Prefs.musicVolume = musicVolume
                    SoundManager.setMusicVolume()
                },
                modifier = Modifier
                    .height(50.dp)
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))


            TextWithShadowAndOutline(
                text = "Sound",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Slider(
                value = soundVolume,
                onValueChange = {
                    soundVolume = it
                    Prefs.soundVolume = soundVolume
                    SoundManager.setSoundVolume()
                },
                modifier = Modifier
                    .height(50.dp)
                    .padding(horizontal = 16.dp)
            )
        }

        // Кнопка "Back"
        Image(
            painter = painterResource(id = R.drawable.backbutton),
            contentDescription = "",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 31.dp)
                .clickable {
                    onBack()
                }
        )
    }
}

@Composable
fun TextWithShadowAndOutline(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit,
    fontWeight: FontWeight = FontWeight.Normal
) {

    Text(
        text = text,
        fontFamily = nujnoefont,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = Color.White,
        style = TextStyle(
            shadow = Shadow(
                color = Color.Black,
                offset = Offset(5f, 5f),
                blurRadius = 2.5f
            ),
            textAlign = TextAlign.Center
        ),
        modifier = modifier
    )
}

