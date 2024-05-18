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

    private val _isAddDeckDialogVisible = mutableStateOf(false)
    val isAddDeckDialogVisible: State<Boolean> = _isAddDeckDialogVisible

    private val _isDeleteDeckDialogVisible = mutableStateOf(false)
    val isDeleteDeckDialogVisible: State<Boolean> = _isDeleteDeckDialogVisible

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
                _isAddDeckDialogVisible.value = true
            }
            DeckEvent.CancelAddDeck -> {
                _isAddDeckDialogVisible.value = false
            }
            is DeckEvent.ConfirmAddDeck -> {
                val deckNameExists = _state.value.decks.map { it.name }.contains(event.name)
                viewModelScope.launch {
                    if (!deckNameExists) {
                        deckUseCases.addDeck(deck = Deck(name = event.name))
                        _isAddDeckDialogVisible.value = false
                    } else {
                        _uiEventFlow.emit(UiEvent.DeckNameExists)
                    }
                }
            }
            DeckEvent.DeleteDeck -> {
                _isDeleteDeckDialogVisible.value = true
            }
            DeckEvent.CancelDeleteDeck -> {
                _isDeleteDeckDialogVisible.value = false
            }
            is DeckEvent.ConfirmDeleteDeck -> {
                _isDeleteDeckDialogVisible.value = false
                viewModelScope.launch {
                    deckUseCases.deleteDeckByName(name = event.name)
                }
            }
            is DeckEvent.EditDeckName -> TODO()
        }
    }

    sealed class UiEvent {
        data object DeckNameExists: UiEvent()
    }
}