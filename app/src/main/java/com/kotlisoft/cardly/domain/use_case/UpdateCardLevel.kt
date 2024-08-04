package com.kotlisoft.cardly.domain.use_case

import com.kotlisoft.cardly.domain.repository.CardRepository
import javax.inject.Inject

class UpdateCardLevel @Inject constructor(
    private val cardRepository: CardRepository,
) {
    suspend operator fun invoke(id: Int, newLevel: Int) {
        cardRepository.updateCardLevel(id, newLevel)
    }
}