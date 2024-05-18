package com.kotlisoft.cardly.presentation.ui.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
) {
    val isDeleteDeckDialogVisible = deckViewModel.isDeleteDeckDialogVisible.value

    if (isDeleteDeckDialogVisible) {
        DeleteDeckDialog(
            onDismissRequest = {
                deckViewModel.onEvent(DeckEvent.CancelDeleteDeck)
            },
            onConfirmRequest = {
                deckViewModel.onEvent(DeckEvent.ConfirmDeleteDeck(deckName))
                onNavigateBack()
            },
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
                        Text(text = deckName)
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
            Text(
                text = "A List Of Cards",
                modifier = Modifier.padding(top = it.calculateTopPadding())
            )
        },
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