package com.kotlisoft.cardly.presentation.ui.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlisoft.cardly.R
import com.kotlisoft.cardly.presentation.ui.components.AccordionItem
import com.kotlisoft.cardly.presentation.ui.components.CardDialog
import com.kotlisoft.cardly.presentation.ui.components.DeleteDialog
import com.kotlisoft.cardly.presentation.ui.components.EditDeckNameDialog
import com.kotlisoft.cardly.presentation.ui.components.NoItemsText
import com.kotlisoft.cardly.presentation.ui.decks.DeckEvent
import com.kotlisoft.cardly.presentation.ui.decks.DeckViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsScreen(
    deckName: String,
    onNavigateBack: () -> Unit,
    onNavigateToQuizScreen: (currentDeckName: String) -> Unit,
    deckViewModel: DeckViewModel = hiltViewModel(),
    cardViewModel: CardViewModel = hiltViewModel(),
) {
    val isDeleteDeckDialogVisible = deckViewModel.state.value.isDeleteDeckDialogVisible
    val isEditDeckNameDialogVisible = deckViewModel.state.value.isEditDeckNameDialogVisible

    val isAddCardDialogVisible = cardViewModel.state.value.isAddCardDialogVisible
    val isEditCardDialogVisible = cardViewModel.state.value.isEditCardDialogVisible
    val isDeleteCardDialogVisible = cardViewModel.state.value.isDeleteCardDialogVisible
    val cards = cardViewModel.state.value.cards

    var currentDeckName by remember { mutableStateOf(deckName) }

    LaunchedEffect(Unit) {
        cardViewModel.initDeckName(currentDeckName)
        cardViewModel.getCards()
    }

    if (isAddCardDialogVisible) {
        CardDialog(
            titleText = stringResource(R.string.add_card),
            confirmButtonText = stringResource(id = R.string.add),
            onDismissRequest = {
                cardViewModel.onEvent(CardEvent.CancelAddCard)
            },
            onConfirmRequest = { question, answer ->
                cardViewModel.onEvent(CardEvent.ConfirmAddCard(question, answer))
            },
        )
    }

    if (isEditCardDialogVisible) {
        CardDialog(
            titleText = stringResource(R.string.edit_card),
            confirmButtonText = stringResource(id = R.string.save),
            initialQuestion = cardViewModel.selectedCard?.question ?: "",
            initialAnswer = cardViewModel.selectedCard?.answer ?: "",
            onDismissRequest = {
                cardViewModel.onEvent(CardEvent.CancelEditCard)
            },
            onConfirmRequest = { question, answer ->
                cardViewModel.onEvent(CardEvent.ConfirmEditCard(deckName, question, answer))
            },
        )
    }

    if (isDeleteCardDialogVisible) {
        DeleteDialog(
            titleText = stringResource(R.string.delete_card),
            contentText = stringResource(R.string.delete_card_content),
            onDismissRequest = {
                cardViewModel.onEvent(CardEvent.CancelDeleteCard)
            },
            onConfirmRequest = {
                cardViewModel.onEvent(CardEvent.ConfirmDeleteCard)
            },
        )
    }

    if (isDeleteDeckDialogVisible) {
        DeleteDialog(
            titleText = stringResource(id = R.string.delete_deck),
            contentText = stringResource(R.string.delete_deck_content),
            onDismissRequest = {
                deckViewModel.onEvent(DeckEvent.CancelDeleteDeck)
            },
            onConfirmRequest = {
                deckViewModel.onEvent(DeckEvent.ConfirmDeleteDeck(currentDeckName))
                onNavigateBack()
            },
        )
    }

    if (isEditDeckNameDialogVisible) {
        EditDeckNameDialog(
            initialDeckName = currentDeckName,
            onDismissRequest = {
                deckViewModel.onEvent(DeckEvent.CancelEditDeckName)
            },
            onConfirmRequest = { newDeckName ->
                deckViewModel.onEvent(DeckEvent.ConfirmEditDeckName(currentDeckName, newDeckName))
                currentDeckName = newDeckName
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Text(
                                text = currentDeckName,
                                modifier = Modifier.padding(horizontal = 8.dp),
                            )
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    deckViewModel.onEvent(DeckEvent.EditDeckName)
                                }
                            )
                            if (cards.isNotEmpty()) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        onNavigateToQuizScreen(currentDeckName)
                                    }
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                deckViewModel.onEvent(DeckEvent.DeleteDeck)
                            }
                        )
                    }
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onNavigateBack()
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        content = {
            LazyColumn(modifier = Modifier.padding(top = it.calculateTopPadding())) {
                if (cards.isNotEmpty()) {
                    items((1..5).toList()) { level ->
                        val cardsForLevel = cards.filter { it.level == level }
                        var areLevelCardsVisible by remember { mutableStateOf(true) }
                        if (cardsForLevel.isNotEmpty()) {
                            AccordionItem(
                                title = stringResource(id = R.string.level, level),
                                isExpanded = areLevelCardsVisible,
                                onExpandChange = { areLevelCardsVisible = !areLevelCardsVisible },
                            ) {
                                Column(modifier = Modifier) {
                                    cardsForLevel.forEach { card ->
                                        CardItem(
                                            card = card,
                                            onEditCard = {
                                                cardViewModel.onEvent(CardEvent.EditCard(card))
                                            },
                                            onDeleteCard = {
                                                cardViewModel.onEvent(CardEvent.DeleteCard(card))
                                            },
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    item {
                        NoItemsText(stringResourceId = R.string.no_cards)
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    cardViewModel.onEvent(CardEvent.AddCard)
                }
            ) {
                Icon(Icons.Filled.Add, stringResource(R.string.floating_action_button))
            }
        }
    )
}