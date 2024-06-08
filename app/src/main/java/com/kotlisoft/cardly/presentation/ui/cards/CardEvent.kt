package com.kotlisoft.cardly.presentation.ui.cards

import com.kotlisoft.cardly.domain.model.Card

sealed class CardEvent {
    data object AddCard: CardEvent()
    data class ConfirmAddCard(val question: String, val answer: String): CardEvent()
    data object CancelAddCard: CardEvent()
    data object DeleteCard: CardEvent()
    data class ConfirmDeleteCard(val cardId: Int): CardEvent()
    data object CancelDeleteCard: CardEvent()
    data class EditCard(val card: Card): CardEvent()
    data object CancelEditCard: CardEvent()
    data class ConfirmEditCard(val deckName: String, val question: String, val answer: String): CardEvent()
}