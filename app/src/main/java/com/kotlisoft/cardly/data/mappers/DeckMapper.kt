package com.kotlisoft.cardly.data.mappers

import com.kotlisoft.cardly.data.local.DeckEntity
import com.kotlisoft.cardly.domain.model.Deck

fun DeckEntity.toDeck(): Deck {
    return Deck(name = name)
}

fun Deck.toDeckEntity(): DeckEntity {
    return DeckEntity(name = name)
}