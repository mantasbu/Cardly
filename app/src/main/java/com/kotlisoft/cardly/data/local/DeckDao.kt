package com.kotlisoft.cardly.data.local

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.kotlisoft.cardly.domain.model.Deck
import kotlinx.coroutines.flow.Flow

interface DeckDao {

    @Insert
    suspend fun insertDeck(deck: DeckEntity)

    @Query("DELETE FROM decks WHERE name = :name")
    suspend fun deleteDeckByName(name: String)

    @Query("SELECT * FROM decks")
    fun getDecks(): Flow<List<Deck>>

    @Query("SELECT * FROM decks WHERE name = :name")
    suspend fun getDeckByName(name: String): Deck

    @Transaction
    @Query("SELECT * FROM decks WHERE name = :deckName")
    suspend fun getDeckWithCards(deckName: String): DeckWithCardsEntity
}