package com.kotlisoft.cardly.domain.use_case

import com.kotlisoft.cardly.domain.model.Card
import com.kotlisoft.cardly.domain.repository.CardRepository
import javax.inject.Inject

class UpdateCard @Inject constructor(
    private val cardRepository: CardRepository,
) {
    suspend operator fun invoke(card: Card, deckName: String) {
        cardRepository.updateCard(card, deckName)
    }
}