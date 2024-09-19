package com.SkyFlyGame.SkywardTreasure

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MenuScreen(
    onStart: () -> Unit,
    onSettings: () -> Unit
){
    val context = LocalContext.current as Activity
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.playbutton),
                contentDescription = "Start Button",
                modifier = Modifier
                    .size(width = 200.dp, height = 100.dp)
                    .clickable {
                        onStart()
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.settingsbutton),
                contentDescription = "Start Button",
                modifier = Modifier
                    .size(width = 200.dp, height = 100.dp)
                    .clickable {
                        onSettings()
                    }
            )

            Spacer(modifier = Modifier.height(52.dp))

            Image(
                painter = painterResource(id = R.drawable.exitbutton),
                contentDescription = "Exit Button",
                modifier = Modifier
                    .size(width = 150.dp, height = 80.dp)
                    .clickable {
                        context.finishAffinity()
                    }
            )
        }
    }
}