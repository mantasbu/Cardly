package com.kotlisoft.cardly.domain.use_case

import com.kotlisoft.cardly.domain.model.Card
import com.kotlisoft.cardly.domain.repository.CardRepository
import javax.inject.Inject

class GetCard @Inject constructor(
    private val cardRepository: CardRepository,
) {
    suspend operator fun invoke(cardId: Int): Card {
        return cardRepository.getCardById(cardId)
    }
}