package com.kotlisoft.cardly.presentation.ui.cards

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlisoft.cardly.domain.model.Card
import com.kotlisoft.cardly.domain.use_case.CardUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val cardUseCases: CardUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(CardsState())
    val state: State<CardsState> = _state

    var selectedCard: Card? = null

    fun initDeckName(deckName: String) {
        _state.value = state.value.copy(deckName = deckName)
    }

    fun getCards() {
        cardUseCases.getCardsByDeck(deckName = _state.value.deckName)
            .onEach { cards ->
                _state.value = state.value.copy(
                    cards = cards,
                )
            }.launchIn(viewModelScope)
    }

    fun onEvent(event: CardEvent) {
        when (event) {
            CardEvent.AddCard -> {
                _state.value = state.value.copy(isAddCardDialogVisible = true)
            }
            CardEvent.CancelAddCard -> {
                _state.value = state.value.copy(isAddCardDialogVisible = false)
            }
            is CardEvent.ConfirmAddCard -> {
                viewModelScope.launch {
                    cardUseCases.addCard(
                        deckName = _state.value.deckName,
                        card = Card(
                            question = event.question,
                            answer = event.answer,
                        ),
                    )
                    _state.value = state.value.copy(isAddCardDialogVisible = false)
                }
            }
            is CardEvent.DeleteCard -> {
                selectedCard = event.card
                _state.value = state.value.copy(isDeleteCardDialogVisible = true)
            }
            CardEvent.CancelDeleteCard -> {
                _state.value = state.value.copy(isDeleteCardDialogVisible = false)
            }
            is CardEvent.ConfirmDeleteCard -> {
                _state.value = state.value.copy(isDeleteCardDialogVisible = false)
                viewModelScope.launch {
                    selectedCard?.let { cardUseCases.deleteCard(cardId = it.id) }
                }
            }
            is CardEvent.EditCard -> {
                selectedCard = event.card
                _state.value = state.value.copy(isEditCardDialogVisible = true)
            }
            CardEvent.CancelEditCard -> {
                _state.value = state.value.copy(isEditCardDialogVisible = false)
            }
            is CardEvent.ConfirmEditCard -> {
                _state.value = state.value.copy(isEditCardDialogVisible = false)
                viewModelScope.launch {
                    selectedCard?.let {
                        cardUseCases.updateCard(
                            deckName = event.deckName,
                            card = it.copy(question = event.question, answer = event.answer),
                        )
                    }
                }
            }
            is CardEvent.UpdateCardLevel -> {
                viewModelScope.launch {
                    cardUseCases.updateCardLevel(id = event.id, newLevel = event.newLevel)
                }
            }
        }
    }
}