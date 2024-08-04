package com.kotlisoft.cardly.presentation.ui.decks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlisoft.cardly.domain.model.Deck
import com.kotlisoft.cardly.domain.use_case.DeckUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeckViewModel @Inject constructor(
    private val deckUseCases: DeckUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(DecksState())
    val state: State<DecksState> = _state

    private val _uiEventFlow = MutableSharedFlow<UiEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    init {
        deckUseCases.getDecks()
            .onEach { decks ->
                _state.value = state.value.copy(
                    decks = decks,
                )
            }.launchIn(viewModelScope)
    }

    fun onEvent(event: DeckEvent) {
        when (event) {
            DeckEvent.AddDeck -> {
                _state.value = state.value.copy(isAddDeckDialogVisible = true)
            }
            DeckEvent.CancelAddDeck -> {
                _state.value = state.value.copy(isAddDeckDialogVisible = false)
            }
            is DeckEvent.ConfirmAddDeck -> {
                val deckNameExists = _state.value.decks.map { it.name }.contains(event.name)
                viewModelScope.launch {
                    if (!deckNameExists) {
                        deckUseCases.addDeck(deck = Deck(name = event.name))
                        _state.value = state.value.copy(isAddDeckDialogVisible = false)
                    } else {
                        _uiEventFlow.emit(UiEvent.DeckNameExists)
                    }
                }
            }
            DeckEvent.DeleteDeck -> {
                _state.value = state.value.copy(isDeleteDeckDialogVisible = true)
            }
            DeckEvent.CancelDeleteDeck -> {
                _state.value = state.value.copy(isDeleteDeckDialogVisible = false)
            }
            is DeckEvent.ConfirmDeleteDeck -> {
                _state.value = state.value.copy(isDeleteDeckDialogVisible = false)
                viewModelScope.launch {
                    deckUseCases.deleteDeckByName(name = event.name)
                    deckUseCases.deleteAllCardsByDeckName(deckName = event.name)
                }
            }
            is DeckEvent.EditDeckName -> {
                _state.value = state.value.copy(isEditDeckNameDialogVisible = true)
            }
            DeckEvent.CancelEditDeckName -> {
                _state.value = state.value.copy(isEditDeckNameDialogVisible = false)
            }
            is DeckEvent.ConfirmEditDeckName -> {
                _state.value = state.value.copy(isEditDeckNameDialogVisible = false)
                viewModelScope.launch {
                    deckUseCases.updateDeckName(event.currentDeckName, event.newDeckName)
                }
            }
        }
    }

    sealed class UiEvent {
        data object DeckNameExists: UiEvent()
    }
}