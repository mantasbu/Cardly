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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.kotlisoft.cardly.presentation.ui.decks.DeckEvent
import com.kotlisoft.cardly.presentation.ui.decks.DeckViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsScreen(
    deckName: String,
    onNavigateBack: () -> Unit,
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
        AddCardDialog(
            onDismissRequest = {
                cardViewModel.onEvent(CardEvent.CancelAddCard)
            },
            onConfirmRequest = { question, answer ->
                cardViewModel.onEvent(CardEvent.ConfirmAddCard(question, answer))
            },
        )
    }

    if (isEditCardDialogVisible) {
        EditCardDialog(
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
        DeleteCardDialog(
            onDismissRequest = {
                cardViewModel.onEvent(CardEvent.CancelDeleteCard)
            },
            onConfirmRequest = {
                cardViewModel.onEvent(CardEvent.ConfirmDeleteCard)
            },
        )
    }

    if (isDeleteDeckDialogVisible) {
        DeleteDeckDialog(
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
            LazyColumn(
                modifier = Modifier.padding(top = it.calculateTopPadding())
            ) {
                items(cards) { card ->
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

@Composable
fun DeleteDeckDialog(
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = stringResource(R.string.delete_deck))
        },
        text = {
            Text(text = stringResource(R.string.delete_deck_content))
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirmRequest()
                }
            ) {
                Text(text = stringResource(R.string.delete))
            }
        },
    )
}

@Composable
fun DeleteCardDialog(
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = stringResource(R.string.delete_card))
        },
        text = {
            Text(text = stringResource(R.string.delete_card_content))
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirmRequest()
                }
            ) {
                Text(text = stringResource(R.string.delete))
            }
        },
    )
}

@Composable
fun EditDeckNameDialog(
    initialDeckName: String,
    onDismissRequest: () -> Unit,
    onConfirmRequest: (String) -> Unit,
) {
    var currentName by remember {
        mutableStateOf(initialDeckName)
    }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "Edit Deck Name")
        },
        text = {
            Column {
                TextField(
                    value = currentName,
                    onValueChange = {
                        currentName = it
                    },
                )
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            Button(
                enabled = currentName.isNotBlank(),
                onClick = {
                    onConfirmRequest(currentName)
                }
            ) {
                Text(text = stringResource(R.string.save))
            }
        },
    )
}

@Composable
fun AddCardDialog(
    onDismissRequest: () -> Unit,
    onConfirmRequest: (question: String, answer: String) -> Unit,
) {
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "Add Card")
        },
        text = {
            Column {
                TextField(
                    value = question,
                    onValueChange = {
                        question = it
                    },
                ) // TODO: Add hints
                TextField(
                    value = answer,
                    onValueChange = {
                        answer = it
                    },
                )
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            Button(
                enabled = question.isNotBlank() && answer.isNotBlank(),
                onClick = {
                    onConfirmRequest(question, answer)
                }
            ) {
                Text(text = stringResource(R.string.add))
            }
        },
    )
}

@Composable
fun EditCardDialog(
    initialQuestion: String,
    initialAnswer: String,
    onDismissRequest: () -> Unit,
    onConfirmRequest: (question: String, answer: String) -> Unit,
) {
    var question by remember { mutableStateOf(initialQuestion) }
    var answer by remember { mutableStateOf(initialAnswer) }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "Edit Card")
        },
        text = {
            Column {
                TextField(
                    value = question,
                    onValueChange = {
                        question = it
                    },
                ) // TODO: Add hints
                TextField(
                    value = answer,
                    onValueChange = {
                        answer = it
                    },
                )
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            Button(
                enabled = question.isNotBlank() && answer.isNotBlank(),
                onClick = {
                    onConfirmRequest(question, answer)
                }
            ) {
                Text(text = stringResource(R.string.save))
            }
        },
    )
}