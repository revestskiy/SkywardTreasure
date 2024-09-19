package com.SkyFlyGame.SkywardTreasure

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.SkyFlyGame.SkywardTreasure.ui.theme.nujnoefont

@Preview
@Composable
fun SettingsScreen() {
    var musicVolume by remember { mutableStateOf(0.5f) }
    var soundVolume by remember { mutableStateOf(0.5f) }

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
                onValueChange = { musicVolume = it },
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))


            TextWithShadowAndOutline(
                text = "Sound",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Slider(
                value = soundVolume,
                onValueChange = { soundVolume = it },
                modifier = Modifier.padding(horizontal = 16.dp)
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

                }
        )
    }
}

@Composable
fun TextWithShadowAndOutline(
    text: String,
    fontSize: TextUnit,
    fontWeight: FontWeight
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
            )
        )
    )
}

