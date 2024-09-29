package com.kotlisoft.cardly.presentation.ui.quiz

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlisoft.cardly.R
import com.kotlisoft.cardly.presentation.ui.cards.CardEvent
import com.kotlisoft.cardly.presentation.ui.cards.CardViewModel
import com.kotlisoft.cardly.presentation.ui.components.FlashCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    deckName: String,
    onSpeak: (textToSpeak: String, isCardFlipped: Boolean) -> Unit,
    onNavigateBack: () -> Unit,
    cardViewModel: CardViewModel = hiltViewModel(),
) {
    val cards = cardViewModel.state.value.cards
    var currentCardIndex by remember { mutableIntStateOf(0) }
    var isCardFlipped by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        cardViewModel.initDeckName(deckName)
        cardViewModel.getCards()
    }

    if (cards.isNotEmpty()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.clickable { onNavigateBack() },
                        )
                    },
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(text = stringResource(R.string.card, currentCardIndex + 1, cards.size))
                            Text(text = stringResource(R.string.level, "${cards[currentCardIndex].level}/5"))
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                )
            },
            content = { paddingValues ->
                FlashCard(
                    modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
                    question = cards[currentCardIndex].question,
                    answer = cards[currentCardIndex].answer,
                    isCardFlipped = isCardFlipped,
                    onFlipCard = { isFlipped ->
                        isCardFlipped = isFlipped
                    }
                )
            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        onClick = {
                            isCardFlipped = false
                            val level = cards[currentCardIndex].level
                            if (level in 2..5) {
                                cardViewModel.onEvent(
                                    CardEvent.UpdateCardLevel(
                                        id = cards[currentCardIndex].id,
                                        newLevel = level - 1
                                    )
                                )
                            }
                            if (cards.size > currentCardIndex + 1) {
                                currentCardIndex++
                            } else {
                                onNavigateBack()
                            }
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
                            val textToSpeak = if (isCardFlipped) {
                                cards[currentCardIndex].answer
                            } else {
                                cards[currentCardIndex].question
                            }
                            onSpeak(textToSpeak, isCardFlipped)
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
                            isCardFlipped = false
                            val level = cards[currentCardIndex].level
                            if (level < 5) {
                                cardViewModel.onEvent(
                                    CardEvent.UpdateCardLevel(
                                        id = cards[currentCardIndex].id,
                                        newLevel = level + 1
                                    )
                                )
                            }
                            if (cards.size > currentCardIndex + 1) {
                                currentCardIndex++
                            } else {
                                onNavigateBack()
                            }
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
            },
        )
    }
}