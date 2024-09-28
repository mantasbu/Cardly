package com.kotlisoft.cardly.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(cardEntity: CardEntity)

    @Query("SELECT * FROM cards WHERE id = :id")
    suspend fun getCardById(id: Int): CardEntity

    @Query("SELECT * FROM cards WHERE deckName = :deck")
    fun getCardsByDeck(deck: String): Flow<List<CardEntity>>

    @Query("DELETE FROM cards WHERE id = :id")
    suspend fun deleteCardById(id: Int)

    @Update
    suspend fun updateCard(cardEntity: CardEntity)

    @Query("UPDATE cards SET level = :newLevel WHERE id = :id")
    suspend fun updateCardLevel(id: Int, newLevel: Int)

    @Transaction
    @Query("SELECT * FROM decks WHERE name = :deckName")
    suspend fun getDeckWithCards(deckName: String): DeckWithCardsEntity

    @Query("DELETE FROM cards")
    suspend fun deleteAllCards()
}