package com.kotlisoft.cardly.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.kotlisoft.cardly.R

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
            Text(text = stringResource(R.string.edit_deck_name))
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
                enabled = currentName.isNotBlank() && currentName != initialDeckName,
                onClick = {
                    onConfirmRequest(currentName)
                }
            ) {
                Text(text = stringResource(R.string.save))
            }
        },
    )
}