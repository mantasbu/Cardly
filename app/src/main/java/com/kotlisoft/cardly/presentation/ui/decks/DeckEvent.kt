package com.kotlisoft.cardly.presentation.ui.decks

sealed class DeckEvent {
    data object AddDeck: DeckEvent()
    data class ConfirmAddDeck(val name: String): DeckEvent()
    data object CancelAddDeck: DeckEvent()
    data object DeleteDeck: DeckEvent()
    data class ConfirmDeleteDeck(val name: String): DeckEvent()
    data object CancelDeleteDeck: DeckEvent()
    data class EditDeckName(val name: String): DeckEvent()
}