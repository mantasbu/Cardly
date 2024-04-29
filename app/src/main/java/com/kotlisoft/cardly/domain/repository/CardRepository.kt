package com.kotlisoft.cardly.domain.repository

import com.kotlisoft.cardly.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {

    suspend fun insertCard(card: Card, deckName: String)

    suspend fun getCardById(id: Int): Card

    fun getCardsByDeck(deck: String): Flow<List<Card>>

    suspend fun deleteCardById(id: Int)
}