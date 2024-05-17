package com.kotlisoft.cardly.domain.use_case

import com.kotlisoft.cardly.domain.repository.CardRepository
import javax.inject.Inject

class DeleteCard @Inject constructor(
    private val cardRepository: CardRepository,
) {
    suspend operator fun invoke(cardId: Int) {
        cardRepository.deleteCardById(cardId)
    }
}