package com.SkyFlyGame.SkywardTreasure


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.random.Random

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GameScreen(
    level: Int = 1,              // Current level,
    levelConfig: LevelConfig = getLevelConfiguration(level),
    targetScore: Int = levelConfig.targetScore,     // Target score to win the level
    onResult: (Result) -> Unit = {},  // Callback to handle the result
    onMenu: () -> Unit = {},  // Callback to go back to the main menu
    onRetry: () -> Unit = {}     // Callback to go back to the main menu,
) {
    var score by remember { mutableIntStateOf(0) }
    var timeLeft by remember {
        mutableIntStateOf(levelConfig.timeLeft)
    }
    var rocketPosition by remember { mutableFloatStateOf(0f) } // Horizontal position of the rocket
    val rocketSpeed = 5f // Reduced speed for smoother movement
    var items by remember { mutableStateOf(listOf<Item>()) } // Generate coins based on level
    var gameOver by remember { mutableStateOf(false) }
    var levelCompleted by remember { mutableStateOf(false) } // Track if the level is completed
    var isMovingLeft by remember { mutableStateOf(false) }
    var isMovingRight by remember { mutableStateOf(false) }
    var paused by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        while (timeLeft > 0) {
            delay(1000L)
            if (!paused) {
                timeLeft -= 1000
            }
        }
        gameOver = true
    }
    if (!gameOver && !levelCompleted) {
        // Game UI
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds
                ), // Pinkish background
            contentAlignment = Alignment.Center
        ) {
            val maxHeight = constraints.maxHeight
            val screenWidth = constraints.maxWidth * 0.3f
            LaunchedEffect(isMovingLeft, isMovingRight) {
                while (isMovingLeft || isMovingRight) {
                    delay(16L) // Frame delay for roughly 60 FPS

                    if (isMovingLeft  && !paused) {
                        // Left boundary limit
                        val leftLimit = -screenWidth + 80f
                        if (rocketPosition > leftLimit) {
                            rocketPosition -= rocketSpeed
                        }
                        else {
                            rocketPosition = leftLimit
                            isMovingLeft = false
                        }
                    }

                    if (isMovingRight  && !paused) {
                        // Right boundary limit
                        val rightLimit = screenWidth - 80f
                        if (rocketPosition < rightLimit) {
                            rocketPosition += rocketSpeed
                        }
                        else {
                            rocketPosition = rightLimit // Set position exactly to the right limit
                            isMovingRight = false // Stop further movement to the right
                        }
                    }
                }
            }


            // Display coins
            items.forEach { coin ->
                CoinItem(
                    coin.x,
                    coin.y,
                    coin.drawableRes
                )
            }
            Box(
                modifier = Modifier
                    .offset(
                        x = rocketPosition.dp,
                        y = (maxHeight * 0.11f).dp
                    )
                    .size(65.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.raketka),
                    contentDescription = "Rocket",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .pointerInteropFilter {
                        when (it.action) {
                            android.view.MotionEvent.ACTION_DOWN -> {
                                isMovingLeft = true
                            }

                            android.view.MotionEvent.ACTION_UP,
                            android.view.MotionEvent.ACTION_CANCEL -> {
                                isMovingLeft = false
                            }
                        }
                        true
                    }
                )
                Box(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .pointerInteropFilter {
                        when (it.action) {
                            android.view.MotionEvent.ACTION_DOWN -> {
                                isMovingRight = true
                            }

                            android.view.MotionEvent.ACTION_UP,
                            android.view.MotionEvent.ACTION_CANCEL -> {
                                isMovingRight = false
                            }
                        }
                        true
                    })
            }
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(0.9f)
                    .paint(
                        painterResource(id = R.drawable.bg_round),
                        contentScale = ContentScale.FillBounds
                    )
                    .align(Alignment.TopCenter),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextWithShadowAndOutline(
                        text = "Timer:${timeLeft.millisFormatted}s",
                        fontSize = 29.sp,
                        modifier = Modifier
                            .padding(8.dp),
                    )
                    TextWithShadowAndOutline(
                        text = "Score:$score/$targetScore",
                        fontSize = 29.sp,
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                    )
                }
                IconButton(
                    onClick = { paused = !paused },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(2.dp)
                        .size(52.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_stop),
                        contentDescription = "Pause",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(32.dp)
                            .alpha(if (!paused) 1f else 0.25f)
                    )
                }
            }
            LaunchedEffect(Unit) {
                items += itemList(level, screenWidth, maxHeight, levelConfig.numberOfCoins)
                while (timeLeft > 0) {
                    delay(10000L)
                    if (!paused) {
                        items += itemList(level, screenWidth, maxHeight, levelConfig.numberOfCoins)
                    }
                }
            }
            LaunchedEffect(Unit) {
                while (timeLeft > 0) {
                    delay(16L)
                    if (paused) continue
                    items = items.map { coin ->
                        val step = if (coin.drawableRes == R.drawable.coin_star) 4f else 8f

                        val leftLimit = -screenWidth + 80f
                        val rightLimit = screenWidth - 80f
                        val newCoin =
                            coin.copy(y = coin.y + step) // Move by smaller steps for smoother animation
                        if (newCoin.y > maxHeight) {
                            // Regenerate coin once it falls off the screen, spawn it above the screen
                            Item(
                                (leftLimit.roundToInt()..rightLimit.roundToInt()).random()
                                    .toFloat(),
                                Random.nextFloat() * -maxHeight - 50f // Spawn coins slightly higher (adjust this value as needed)
                            )
                        }
                        else {
                            newCoin
                        }
                    }

                    // Collision detection and filtering out collected coins
                    items = items.filter { item ->
                        val itemCollected =
                            item.y > (maxHeight * 0.11f) && item.y < (maxHeight * 0.11f + 80) &&
                                    abs(item.x - rocketPosition) < 50
                        val planetCollected =
                            itemCollected && item.drawableRes != R.drawable.coin_star
                        if (planetCollected) {
                            gameOver = true
                            levelCompleted = false
                        }
                        else if (itemCollected) {
                            score += 20 // Increment score if the item is collected
                        }
                        !itemCollected // Only keep coins that are not collected
                    }

                    // Check if the player won the level
                    if (score >= targetScore) {
                        levelCompleted = true
                    }
                }
            }
        }
    }
    else {
        LaunchedEffect(key1 = levelCompleted) {
            if (levelCompleted) {
                Prefs.levelPassed(level)
            }
            onResult(
               Result(
                   isWin = levelCompleted,
                   level = level,
                   score = score,
                   targetScore = targetScore,
                   timer = timeLeft,
               )
            )
        }
    }
}


// Coin data class to store position
data class Item(val x: Float, val y: Float, val drawableRes: Int = R.drawable.coin_star)

// Coin composable for drawing a coin
@Composable
fun CoinItem(x: Float, y: Float, drawableRes: Int = R.drawable.coin_star) {
    Image(
        modifier = Modifier
            .offset(x = x.dp, y = y.dp)
            .size(
                if (drawableRes == R.drawable.dart) 80.dp else 40.dp
            ),
        painter = painterResource(id = drawableRes),
        contentDescription = "Coin",
        contentScale = ContentScale.Fit
    )
}

// Function to generate initial set of coins based on the level
fun itemList(level: Int, screenWidth: Float, height: Int, numberOfCoins: Int): List<Item> {
    val listDrawables = listOf(
        R.drawable.coin_star,
        R.drawable.dart,
    )
    val leftLimit = -screenWidth + 80f
    val rightLimit = screenWidth - 80f
    return List(numberOfCoins) {
        Item(
            (leftLimit.roundToInt()..rightLimit.roundToInt()).random().toFloat(),
            Random.nextFloat() * -height - 50f,
            listDrawables.random()
        ) // Coins spawn off-screen above the visible area
    }
}

fun getLevelConfiguration(level: Int): LevelConfig {
    return when (level) {
        in 1..10 -> LevelConfig(
            targetScore = 100 + (level - 1) * 20, // увеличение цели на 50 очков за уровень
            timeLeft = 30000 + (level - 1) * 2000, // уменьшение времени на 2 секунды за уровень
            numberOfCoins = 3 + level // увеличение количества монет
        )
        in 11..20 -> LevelConfig(
            targetScore = 600 + (level - 11) * 30, // увеличение цели на 60 очков за уровень
            timeLeft = 28000 + (level - 11) * 1500, // уменьшение времени на 1.5 секунды за уровень
            numberOfCoins = 10 + (level - 11) // больше монет
        )
        in 21..30 -> LevelConfig(
            targetScore = 1200 + (level - 21) * 40,
            timeLeft = 25000 + (level - 21) * 1000,
            numberOfCoins = 15 + (level - 21)
        )
        in 31..40 -> LevelConfig(
            targetScore = 1900 + (level - 31) * 80,
            timeLeft = 22000 + (level - 31) * 500,
            numberOfCoins = 20 + (level - 31)
        )
        else -> LevelConfig(
            targetScore = 0,
            timeLeft = 0,
            numberOfCoins = 0
        )
    }
}

data class LevelConfig(
    val targetScore: Int,    // Цель по очкам для победы
    val timeLeft: Int,       // Время на прохождение уровня в миллисекундах
    val numberOfCoins: Int   // Количество монет, которые нужно собрать
)

val Number.millisFormatted: String
    get() = SimpleDateFormat("mm:ss", Locale.getDefault()).format(this)
