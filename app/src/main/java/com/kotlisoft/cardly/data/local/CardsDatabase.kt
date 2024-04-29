package com.kotlisoft.cardly.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        CardEntity::class,
        DeckEntity::class,
    ],
    version = 1,
)
abstract class CardsDatabase: RoomDatabase() {

    abstract val cardDao: CardDao
    abstract val deckDao: DeckDao

    companion object {
        const val DATABASE_NAME = "cards_db"
    }
}
