package com.kotlisoft.cardly.presentation.ui.decks

import com.kotlisoft.cardly.domain.model.Deck

data class DecksState(
    val decks: List<Deck> = emptyList(),
)
