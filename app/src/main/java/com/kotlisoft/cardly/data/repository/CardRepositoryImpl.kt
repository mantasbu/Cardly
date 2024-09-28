package com.kotlisoft.cardly.data.repository

import com.kotlisoft.cardly.data.local.CardDao
import com.kotlisoft.cardly.data.local.CardEntity
import com.kotlisoft.cardly.data.mappers.toCard
import com.kotlisoft.cardly.data.mappers.toDeckWithCards
import com.kotlisoft.cardly.domain.model.Card
import com.kotlisoft.cardly.domain.model.DeckWithCards
import com.kotlisoft.cardly.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardRepositoryImpl @Inject constructor(
    private val dao: CardDao,
) : CardRepository {

    override suspend fun insertCard(card: Card, deckName: String) {
        dao.insertCard(
            CardEntity(
                deckName = deckName,
                question = card.question,
                answer = card.answer
            ),
        )
    }

    override suspend fun getCardById(id: Int): Card {
        return dao.getCardById(id).toCard()
    }

    override fun getCardsByDeck(deck: String): Flow<List<Card>> {
        return dao.getCardsByDeck(deck).map { it.map { it.toCard() } }
    }

    override suspend fun updateCard(card: Card, deckName: String) {
        val updatedCard = CardEntity(
            id = card.id,
            question = card.question,
            answer = card.answer,
            deckName = deckName,
        )
        dao.updateCard(updatedCard)
    }

    override suspend fun updateCardLevel(id: Int, newLevel: Int) {
        dao.updateCardLevel(id, newLevel)
    }

    override suspend fun deleteCardById(id: Int) {
        dao.deleteCardById(id)
    }

    override suspend fun getDeckWithCards(deckName: String): DeckWithCards {
        return dao.getDeckWithCards(deckName).toDeckWithCards()
    }

    override suspend fun deleteAllCards() {
        dao.deleteAllCards()
    }
}