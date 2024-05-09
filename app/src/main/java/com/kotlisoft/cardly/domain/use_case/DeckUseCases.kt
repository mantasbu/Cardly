package com.kotlisoft.cardly.domain.use_case

data class DeckUseCases(
    val addDeck: AddDeck,
    val deleteDeckByName: DeleteDeckByName,
    val getDecks: GetDecks,
)
