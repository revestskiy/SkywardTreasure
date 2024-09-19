package com.SkyFlyGame.SkywardTreasure

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.SkyFlyGame.SkywardTreasure.ui.theme.GrayColor
import com.SkyFlyGame.SkywardTreasure.ui.theme.SkyColor
import com.SkyFlyGame.SkywardTreasure.ui.theme.SkywardTreasureTheme
import com.google.gson.Gson
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Prefs.init(application)
        SoundManager.init(application)
        super.onCreate(savedInstanceState)
        setContent {
            SkywardTreasureTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "loading") {
                    composable("loading") {
                        LoadingScreen {
                            navController.navigate("menu") {
                                popUpTo("loading") {
                                    inclusive = true
                                }
                            }
                        }
                    }
                    composable("menu") {
                        MenuScreen(
                            onStart = {
                                navController.navigate("levels")
                            },
                            onSettings = {
                                navController.navigate("settings")
                            }
                        )
                    }
                    composable("levels") {
                        LevelsScreen {
                            navController.navigate("game/$it")
                        }
                    }
                    composable("settings") {
                        SettingsScreen(navController::popBackStack)
                    }
                    composable("game/{level}") {
                        val level = it.arguments?.getString("level")?.toInt()!!
                        GameScreen(
                            level = level,
                            onMenu = {
                                navController.navigate("menu") {
                                    popUpTo("game/$level") {
                                        inclusive = true
                                    }
                                }
                            },
                            onResult = { result ->
                                val json = Uri.encode(Gson().toJson(result))
                                navController.navigate("result/$json")
                            },
                            onBack = navController::popBackStack
                        )
                    }
                    composable(route = "result/{result}",
                        arguments = listOf(
                            navArgument("result") { type = ResultType() }
                        )
                    ) {
                        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            it.arguments?.getParcelable("result", Result::class.java)
                        }
                        else {
                            it.arguments?.getParcelable("result")
                        }!!
                        LevelCompleteScreen(
                            isWin = result.isWin,
                            level = result.level,
                            score = result.score,
                            targetScore = result.targetScore,
                            timer = result.timer,
                            onMenu = {
                                navController.navigate("menu") {
                                    popUpTo("result/$result") {
                                        inclusive = true
                                    }
                                }
                            }) {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun LoadingScreen(
    function: () -> Unit
) {
    var progress by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(Unit) {
        repeat(10) {
            progress += 0.1f
            delay(200L)
        }
        function()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.loading),
                contentScale = ContentScale.Crop
            )
    ) {
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(50.dp)
                .padding(bottom = 32.dp),
            color = SkyColor,
            trackColor = GrayColor,
            strokeCap = StrokeCap.Round
        )
    }
}

class ResultType : NavType<Result>(false) {
    override fun get(bundle: Bundle, key: String): Result? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, Result::class.java)
        }
        else {
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): Result {
        return Gson().fromJson(value, Result::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Result) {
        bundle.putParcelable(key, value)
    }
}
