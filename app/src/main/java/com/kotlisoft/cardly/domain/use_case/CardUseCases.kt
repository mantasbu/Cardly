package com.kotlisoft.cardly.domain.use_case

data class CardUseCases(
    val addCard: AddCard,
    val getCard: GetCard,
    val deleteCard: DeleteCard,
    val getCardsByDeck: GetCardsByDeck,
)
