package com.kotlisoft.cardly.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.kotlisoft.cardly.domain.model.Deck
import kotlinx.coroutines.flow.Flow

@Dao
interface DeckDao {
    @Insert
    suspend fun insertDeck(deck: DeckEntity)

    @Query("DELETE FROM decks WHERE name = :name")
    suspend fun deleteDeckByName(name: String)

    @Transaction
    @Query("DELETE FROM cards WHERE deckName = :deckName")
    suspend fun deleteAllCardsByDeckName(deckName: String)

    @Query("SELECT * FROM decks")
    fun getDecks(): Flow<List<Deck>>

    @Query("UPDATE decks SET name = :newDeckName WHERE name = :currentDeckName")
    suspend fun updateDeckName(currentDeckName: String, newDeckName: String)

    @Query("DELETE FROM decks")
    suspend fun deleteAllDecks()
}