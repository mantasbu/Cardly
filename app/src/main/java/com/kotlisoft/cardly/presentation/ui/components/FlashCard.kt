package com.kotlisoft.cardly.presentation.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun FlashCard(
    question: String,
    answer: String,
    onSpeak: (textToSpeak: String, isCardFlipped: Boolean) -> Unit,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
) {
    var flipped by remember { mutableStateOf(false) }
    val rotationY = remember { Animatable(0f) }

    LaunchedEffect(flipped) {
        rotationY.animateTo(
            targetValue = if (flipped) 180f else 0f,
            animationSpec = TweenSpec(durationMillis = 500)
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
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
                    onClick = { flipped = !flipped },
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
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
                    flipped = false
                    onNegativeClick()
                },
                shape = CircleShape,
                modifier = Modifier.size(80.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                )
            }
            Spacer(modifier = Modifier.size(32.dp))
            Button(
                onClick = {
                    val textToSpeak = if (flipped) answer else question
                    onSpeak(textToSpeak, flipped)
                },
                shape = CircleShape,
                modifier = Modifier.size(80.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                )
            }
            Spacer(modifier = Modifier.size(32.dp))
            Button(
                onClick = {
                    flipped = false
                    onPositiveClick()
                },
                shape = CircleShape,
                modifier = Modifier.size(80.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                )
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
                onSpeak = { _, _ -> },
                onNegativeClick = {},
                onPositiveClick = {},
            )
        }
    }
}