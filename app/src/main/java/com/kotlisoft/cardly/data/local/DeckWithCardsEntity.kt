package com.kotlisoft.cardly.data.local

import androidx.room.Embedded
import androidx.room.Relation

data class DeckWithCardsEntity(
    @Embedded
    val deck: DeckEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "deckName",
    )
    val cards: List<CardEntity>,
)
