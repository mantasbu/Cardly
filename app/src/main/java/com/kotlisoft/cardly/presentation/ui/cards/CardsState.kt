package com.kotlisoft.cardly.presentation.ui.cards

import com.kotlisoft.cardly.domain.model.Card

data class CardsState(
    val deckName: String = "",
    val cards: List<Card> = emptyList(),
    val isAddCardDialogVisible: Boolean = false,
    val isDeleteCardDialogVisible: Boolean = false,
    val isEditCardDialogVisible: Boolean = false,
)
