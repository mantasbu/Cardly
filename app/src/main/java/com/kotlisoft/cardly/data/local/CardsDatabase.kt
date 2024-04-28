package com.kotlisoft.cardly.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CardEntity::class],
    version = 1,
)
abstract class CardsDatabase: RoomDatabase() {
    abstract val dao: CardDao
}
