package com.kotlisoft.cardly.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val deckName: String,
    val question: String,
    val answer: String,
    val level: Int = 1,
)
