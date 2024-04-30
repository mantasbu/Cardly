package com.kotlisoft.cardly.data.repository

import com.kotlisoft.cardly.data.local.DeckDao
import com.kotlisoft.cardly.data.local.DeckEntity
import com.kotlisoft.cardly.data.mappers.toDeckWithCards
import com.kotlisoft.cardly.domain.model.Deck
import com.kotlisoft.cardly.domain.model.DeckWithCards
import com.kotlisoft.cardly.domain.repository.DeckRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeckRepositoryImpl @Inject constructor(
    private val dao: DeckDao,
) : DeckRepository {
    override suspend fun insertDeck(deck: Deck) {
        dao.insertDeck(DeckEntity(name = deck.name))
    }

    override suspend fun deleteDeckByName(name: String) {
        dao.deleteDeckByName(name)
    }

    override fun getDecks(): Flow<List<Deck>> {
        return dao.getDecks()
    }

    override suspend fun getDeckByName(name: String): Deck {
        return dao.getDeckByName(name)
    }

    override suspend fun getDeckWithCards(deckName: String): DeckWithCards {
        return dao.getDeckWithCards(deckName).toDeckWithCards()
    }
}