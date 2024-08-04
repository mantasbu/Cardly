package com.kotlisoft.cardly.domain.repository

import com.kotlisoft.cardly.domain.model.Deck
import kotlinx.coroutines.flow.Flow

interface DeckRepository {

    suspend fun insertDeck(deck: Deck)

    suspend fun deleteDeckByName(name: String)

    suspend fun deleteAllCardsByDeckName(deckName: String)

    fun getDecks(): Flow<List<Deck>>

    suspend fun updateDeckName(currentDeckName: String, newDeckName: String)
}