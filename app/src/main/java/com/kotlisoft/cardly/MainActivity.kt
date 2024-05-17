package com.kotlisoft.cardly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kotlisoft.cardly.presentation.ui.cards.CardsScreen
import com.kotlisoft.cardly.presentation.ui.decks.HomeScreen
import com.kotlisoft.cardly.presentation.ui.navigation.NavArgs
import com.kotlisoft.cardly.presentation.ui.navigation.Routes
import com.kotlisoft.cardly.presentation.ui.theme.CardlyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            CardlyTheme {
                NavHost(
                    navController = navController,
                    startDestination = Routes.HOME.name,
                ) {
                    composable(route = Routes.HOME.name) {
                        HomeScreen(
                            onNavigateToDeckCards = {
                                navController.navigate(route = "${Routes.CARDS.name}/$it")
                            },
                        )
                    }
                    composable(
                        route = "${Routes.CARDS.name}/{${NavArgs.DECK_NAME}}",
                        arguments = listOf(
                            navArgument(name = NavArgs.DECK_NAME.name) {
                                type = NavType.StringType
                            },
                        ),
                    ) {
                        val deckName = it.arguments?.getString(NavArgs.DECK_NAME.name) ?: ""
                        CardsScreen(deckName)
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