package com.kotlisoft.cardly.presentation.ui.cards

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsScreen(
    deckName: String,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = deckName)
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