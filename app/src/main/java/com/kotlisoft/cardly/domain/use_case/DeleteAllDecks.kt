package com.kotlisoft.cardly.domain.use_case

import com.kotlisoft.cardly.domain.repository.DeckRepository
import javax.inject.Inject

class DeleteAllDecks @Inject constructor(
    private val deckRepository: DeckRepository,
) {
    suspend operator fun invoke() {
        deckRepository.deleteAllDecks()
    }
}