package com.kotlisoft.cardly.domain.use_case

import com.kotlisoft.cardly.domain.model.Card
import com.kotlisoft.cardly.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCardsByDeck @Inject constructor(
    private val cardRepository: CardRepository,
) {
    operator fun invoke(deckName: String): Flow<List<Card>> {
        return cardRepository.getCardsByDeck(deckName)
    }
}