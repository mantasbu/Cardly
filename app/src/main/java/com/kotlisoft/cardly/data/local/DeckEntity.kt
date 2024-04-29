package com.kotlisoft.cardly.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "decks")
data class DeckEntity(
    @PrimaryKey
    val name: String,
)
