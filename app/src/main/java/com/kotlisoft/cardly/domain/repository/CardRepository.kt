package com.kotlisoft.cardly.domain.repository

import com.kotlisoft.cardly.domain.model.Card
import com.kotlisoft.cardly.domain.model.DeckWithCards
import kotlinx.coroutines.flow.Flow

interface CardRepository {

    suspend fun insertCard(card: Card, deckName: String)

    suspend fun getCardById(id: Int): Card

    fun getCardsByDeck(deck: String): Flow<List<Card>>

    suspend fun updateCard(card: Card, deckName: String)

    suspend fun updateCardLevel(id: Int, newLevel: Int)

    suspend fun deleteCardById(id: Int)

    suspend fun getDeckWithCards(deckName: String): DeckWithCards
}