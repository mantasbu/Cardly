package com.kotlisoft.cardly.domain.model

data class DeckWithCards(
    val deck: Deck,
    val cards: List<Card>,
)
