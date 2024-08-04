package com.kotlisoft.cardly.presentation.ui.decks

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlisoft.cardly.R
import com.kotlisoft.cardly.presentation.ui.components.NoItemsText
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDeckCards: (String) -> Unit,
    viewModel: DeckViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val isAddDeckDialogVisible = viewModel.state.value.isAddDeckDialogVisible
    val decks = viewModel.state.value.decks

    LaunchedEffect(Unit) {
        viewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                DeckViewModel.UiEvent.DeckNameExists -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.deck_name_already_exists),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    if (isAddDeckDialogVisible) {
        AddDeckDialog(
            onDismissRequest = {
                viewModel.onEvent(DeckEvent.CancelAddDeck)
            },
            onConfirmRequest = { name ->
                viewModel.onEvent(DeckEvent.ConfirmAddDeck(name))
            },
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.decks))
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
                if (decks.isNotEmpty()) {
                    items(decks) { deck ->
                        DeckItem(
                            deck = deck,
                            onClick = {
                                onNavigateToDeckCards(deck.name)
                            },
                        )
                    }
                } else {
                    item {
                        NoItemsText(stringResourceId = R.string.no_decks)
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(DeckEvent.AddDeck)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.floating_action_button),
                )
            }
        }
    )
}

@Composable
private fun AddDeckDialog(
    onDismissRequest: () -> Unit,
    onConfirmRequest: (String) -> Unit,
) {
    var name by remember {
        mutableStateOf("")
    }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = stringResource(R.string.add_deck))
        },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = {
                        name = it
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
                enabled = name.isNotBlank(),
                onClick = {
                    onConfirmRequest(name)
                }
            ) {
                Text(text = stringResource(R.string.add))
            }
        },
    )
}