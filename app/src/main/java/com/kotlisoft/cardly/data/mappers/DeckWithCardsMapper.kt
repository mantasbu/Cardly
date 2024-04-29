package com.kotlisoft.cardly.data.mappers

import com.kotlisoft.cardly.data.local.DeckWithCardsEntity
import com.kotlisoft.cardly.domain.model.DeckWithCards

fun DeckWithCardsEntity.toDeckWithCards(): DeckWithCards {
    return DeckWithCards(
        deck = deck.toDeck(),
        cards = cards.map { it.toCard() },
    )
}