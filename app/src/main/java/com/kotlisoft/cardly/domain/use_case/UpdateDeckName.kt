package com.kotlisoft.cardly.domain.use_case

import com.kotlisoft.cardly.domain.repository.DeckRepository
import javax.inject.Inject

class UpdateDeckName @Inject constructor(
    private val deckRepository: DeckRepository,
) {
    suspend operator fun invoke(currentDeckName: String, newDeckName: String) {
        deckRepository.updateDeckName(currentDeckName, newDeckName)
    }
}