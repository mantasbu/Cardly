package com.kotlisoft.cardly.domain.repository

import com.kotlisoft.cardly.domain.model.Deck
import com.kotlisoft.cardly.domain.model.DeckWithCards
import kotlinx.coroutines.flow.Flow

interface DeckRepository {

    suspend fun insertDeck(deck: Deck)

    suspend fun deleteDeckByName(name: String)

    fun getDecks(): Flow<List<Deck>>

    suspend fun updateDeckName(currentDeckName: String, newDeckName: String)

    suspend fun getDeckWithCards(deckName: String): DeckWithCards
}