package com.kotlisoft.cardly.domain.use_case

import com.kotlisoft.cardly.domain.repository.CardRepository
import javax.inject.Inject

class DeleteAllCards @Inject constructor(
    private val cardRepository: CardRepository,
) {
    suspend operator fun invoke() {
        cardRepository.deleteAllCards()
    }
}