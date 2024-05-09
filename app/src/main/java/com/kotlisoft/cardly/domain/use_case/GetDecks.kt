package com.kotlisoft.cardly.domain.use_case

import com.kotlisoft.cardly.domain.model.Deck
import com.kotlisoft.cardly.domain.repository.DeckRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDecks @Inject constructor(
    private val deckRepository: DeckRepository,
) {
    operator fun invoke(): Flow<List<Deck>> {
        return deckRepository.getDecks()
    }
}