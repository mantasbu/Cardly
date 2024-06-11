package com.kotlisoft.cardly.presentation.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.kotlisoft.cardly.R

@Composable
fun DeleteDialog(
    titleText: String,
    contentText: String,
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = titleText)
        },
        text = {
            Text(text = contentText)
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