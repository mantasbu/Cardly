package com.kotlisoft.cardly.presentation.ui.decks

sealed class DeckEvent {
    data object AddDeck: DeckEvent()
    data class ConfirmAddDeck(val name: String): DeckEvent()
    data object CancelAddDeck: DeckEvent()
    data object DeleteDeck: DeckEvent()
    data class ConfirmDeleteDeck(val name: String): DeckEvent()
    data object CancelDeleteDeck: DeckEvent()
    data object EditDeckName: DeckEvent()
    data object CancelEditDeckName: DeckEvent()
    data class ConfirmEditDeckName(
        val currentDeckName: String,
        val newDeckName: String,
    ): DeckEvent()
}