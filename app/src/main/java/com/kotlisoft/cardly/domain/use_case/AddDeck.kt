package com.kotlisoft.cardly.domain.use_case

import com.kotlisoft.cardly.domain.model.Deck
import com.kotlisoft.cardly.domain.model.InvalidDeckException
import com.kotlisoft.cardly.domain.repository.DeckRepository
import javax.inject.Inject

class AddDeck @Inject constructor(
    private val deckRepository: DeckRepository,
) {
    @Throws(InvalidDeckException::class)
    suspend operator fun invoke(deck: Deck) {
        if(deck.name.isBlank()) {
            throw InvalidDeckException("The name of the deck can't be empty.")
        }
        deckRepository.insertDeck(deck)
    }
}