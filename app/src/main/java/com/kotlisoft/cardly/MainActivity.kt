package com.kotlisoft.cardly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kotlisoft.cardly.presentation.ui.theme.CardlyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            CardlyTheme {
                NavHost(
                    navController = navController,
                    startDestination = Routes.DECKS.name,
                ) {
                    composable(route = Routes.DECKS.name) {
                        // List of all available topics to learn from
                    }
                    composable(route = Routes.CARDS.name) {
                        // Once user clicks on a topic, he is taken to the cards screen for learning
                    }
                    composable(route = Routes.QUIZ.name) {
                        // Once user clicks on a topic, he is taken to the quiz screen for practising
                    }
                    composable(route = Routes.CARD_SETTINGS.name) {
                        // Allow user to create, update and delete the flash cards
                    }
                    composable(route = Routes.PROGRESS.name) {
                        // Allow user to track progress of their flash cards
                    }
                }
            }
        }
    }
}

private enum class Routes {
    DECKS,
    CARDS,
    QUIZ,
    CARD_SETTINGS,
    PROGRESS,
}