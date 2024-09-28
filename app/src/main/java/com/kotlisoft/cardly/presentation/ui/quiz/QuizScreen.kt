package com.kotlisoft.cardly.presentation.ui.quiz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlisoft.cardly.R
import com.kotlisoft.cardly.presentation.ui.cards.CardEvent
import com.kotlisoft.cardly.presentation.ui.cards.CardViewModel
import com.kotlisoft.cardly.presentation.ui.components.FlashCard

@Composable
fun QuizScreen(
    deckName: String,
    onSpeak: (textToSpeak: String, isCardFlipped: Boolean) -> Unit,
    onNavigateBack: () -> Unit,
    cardViewModel: CardViewModel = hiltViewModel(),
) {
    val cards = cardViewModel.state.value.cards
    var currentCardIndex by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(Unit) {
        cardViewModel.initDeckName(deckName)
        cardViewModel.getCards()
    }

    if (cards.isNotEmpty()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.card, currentCardIndex + 1, cards.size),
                modifier = Modifier.padding(top = 16.dp),
            )
            Text(
                text = stringResource(R.string.level, "${cards[currentCardIndex].level}/5"),
                modifier = Modifier.padding(top = 16.dp),
            )
            FlashCard(
                question = cards[currentCardIndex].question,
                answer = cards[currentCardIndex].answer,
                onSpeak = onSpeak,
                onPositiveClick = {
                    if (cards[currentCardIndex].level < 5) {
                        cardViewModel.onEvent(
                            CardEvent.UpdateCardLevel(
                                id = cards[currentCardIndex].id,
                                newLevel = cards[currentCardIndex].level + 1
                            )
                        )
                    }
                    if (cards.size > currentCardIndex + 1) {
                        currentCardIndex++
                    } else {
                        onNavigateBack()
                    }
                },
                onNegativeClick = {
                    val level = cards[currentCardIndex].level
                    if (level in 2..5) {
                        cardViewModel.onEvent(
                            CardEvent.UpdateCardLevel(
                                id = cards[currentCardIndex].id,
                                newLevel = cards[currentCardIndex].level - 1
                            )
                        )
                    }
                    if (cards.size > currentCardIndex + 1) {
                        currentCardIndex++
                    } else {
                        onNavigateBack()
                    }
                }
            )
        }
    }
}