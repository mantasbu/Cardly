package com.kotlisoft.cardly.domain.use_case

import com.kotlisoft.cardly.domain.model.DeckWithCards
import com.kotlisoft.cardly.domain.repository.CardRepository
import javax.inject.Inject

class GetDeckWithCards @Inject constructor(
    private val cardRepository: CardRepository,
) {
    suspend operator fun invoke(deckName: String): DeckWithCards {
        return cardRepository.getDeckWithCards(deckName)
    }
}