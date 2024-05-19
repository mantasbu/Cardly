package com.kotlisoft.cardly.presentation.ui.decks

import com.kotlisoft.cardly.domain.model.Deck

data class DecksState(
    val decks: List<Deck> = emptyList(),
    val isAddDeckDialogVisible: Boolean = false,
    val isDeleteDeckDialogVisible: Boolean = false,
    val isEditDeckNameDialogVisible: Boolean = false,
)
