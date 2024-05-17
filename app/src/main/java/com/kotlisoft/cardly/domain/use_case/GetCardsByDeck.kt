package com.kotlisoft.cardly.domain.use_case

import com.kotlisoft.cardly.domain.repository.CardRepository
import javax.inject.Inject

class GetCardsByDeck @Inject constructor(
    private val cardRepository: CardRepository,
) {
    operator fun invoke(deckName: String) {
        cardRepository.getCardsByDeck(deckName)
    }
}