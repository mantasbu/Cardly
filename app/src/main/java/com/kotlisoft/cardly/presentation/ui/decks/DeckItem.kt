package com.kotlisoft.cardly.presentation.ui.decks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kotlisoft.cardly.domain.model.Deck

@Composable
fun DeckItem(
    deck: Deck,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                onClick()
            },
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(16.dp)
                .height(32.dp),
        ) {
            Text(text = deck.name)
        }
    }
}

@Preview
@Composable
private fun DeckItemPreview() {
    DeckItem(
        deck = Deck(name = "Spanish/English Language Cards"),
        onClick = {},
    )
}