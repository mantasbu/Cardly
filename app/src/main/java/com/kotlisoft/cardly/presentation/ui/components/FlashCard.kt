package com.kotlisoft.cardly.presentation.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun FlashCard(
    modifier: Modifier = Modifier,
    question: String,
    answer: String,
    isCardFlipped: Boolean,
    onFlipCard: (Boolean) -> Unit,
) {
    val rotationY = remember { Animatable(0f) }

    LaunchedEffect(isCardFlipped) {
        rotationY.animateTo(
            targetValue = if (isCardFlipped) 180f else 0f,
            animationSpec = TweenSpec(durationMillis = 500)
        )
    }

    Column(modifier = modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .padding(
                    start = 32.dp,
                    end = 32.dp,
                    top = 16.dp,
                )
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .graphicsLayer {
                    this.rotationY = rotationY.value
                    cameraDistance = 12f * density
                }
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = { onFlipCard(!isCardFlipped) },
                ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (rotationY.value <= 90f || rotationY.value > 270f) {
                    Text(
                        text = question,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.graphicsLayer {
                            this.rotationY = 0f
                        }
                    )
                } else {
                    Text(
                        text = answer,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .graphicsLayer {
                                this.rotationY = 180f
                            }
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun FlashCardPreview() {
    MaterialTheme {
        Surface {
            FlashCard(
                question = "What is Jetpack Compose?",
                answer = "A modern toolkit for building native Android UI using declarative components.",
                isCardFlipped = false,
                onFlipCard = {},
            )
        }
    }
}