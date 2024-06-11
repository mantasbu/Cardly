package com.kotlisoft.cardly.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.kotlisoft.cardly.R

@Composable
fun CardDialog(
    titleText: String,
    confirmButtonText: String,
    initialQuestion: String = "",
    initialAnswer: String = "",
    onDismissRequest: () -> Unit,
    onConfirmRequest: (question: String, answer: String) -> Unit,
) {
    var question by remember { mutableStateOf(initialQuestion) }
    var answer by remember { mutableStateOf(initialAnswer) }
    val isConfirmButtonEnabled by remember(question, answer) {
        derivedStateOf {
            question.isNotBlank() && answer.isNotBlank() &&
                    question != initialQuestion && answer != initialAnswer
        }
    }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = titleText)
        },
        text = {
            Column {
                TextField(
                    value = question,
                    placeholder = {
                        Text(stringResource(R.string.enter_question))
                    },
                    onValueChange = {
                        question = it
                    },
                )
                TextField(
                    value = answer,
                    placeholder = {
                        Text(stringResource(R.string.enter_answer))
                    },
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
                enabled = isConfirmButtonEnabled,
                onClick = {
                    onConfirmRequest(question, answer)
                }
            ) {
                Text(text = confirmButtonText)
            }
        },
    )
}