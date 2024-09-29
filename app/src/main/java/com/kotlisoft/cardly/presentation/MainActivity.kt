package com.kotlisoft.cardly.presentation

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kotlisoft.cardly.presentation.ui.cards.CardsScreen
import com.kotlisoft.cardly.presentation.ui.decks.HomeScreen
import com.kotlisoft.cardly.presentation.ui.navigation.NavArgs
import com.kotlisoft.cardly.presentation.ui.navigation.Routes
import com.kotlisoft.cardly.presentation.ui.quiz.QuizScreen
import com.kotlisoft.cardly.presentation.ui.settings.SettingLocale
import com.kotlisoft.cardly.presentation.ui.settings.SettingsScreen
import com.kotlisoft.cardly.presentation.ui.theme.CardlyTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    private lateinit var textToSpeech: TextToSpeech
    private lateinit var textToSpeechInSpanish: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        textToSpeech = TextToSpeech(this) { status ->
            onInit(status)
        }

        textToSpeechInSpanish = TextToSpeech(this) { status ->
            onInitSpanish(status)
        }

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
                            onNavigateToSettings = {
                                navController.navigate(route = "${Routes.SETTINGS.name}/$it")
                            }
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
                        CardsScreen(
                            deckName = deckName,
                            onNavigateToQuizScreen = {
                                navController.navigate(route = "${Routes.QUIZ.name}/$it")
                            },
                            onNavigateBack = navController::popBackStack,
                        )
                    }
                    composable(
                        route = "${Routes.QUIZ.name}/{${NavArgs.DECK_NAME}}",
                        arguments = listOf(
                            navArgument(name = NavArgs.DECK_NAME.name) {
                                type = NavType.StringType
                            },
                        ),
                    ) {
                        val deckName = it.arguments?.getString(NavArgs.DECK_NAME.name) ?: ""
                        val settingsState by remember(viewModel.state.value) {
                            mutableStateOf(viewModel.state.value)
                        }
                        viewModel.loadSettings()
                        QuizScreen(
                            deckName = deckName,
                            onSpeak = { textToSpeak, isCardFlipped ->
                                val shouldSpeakSpanish =
                                    (isCardFlipped && settingsState.answerLocale == SettingLocale.ES) ||
                                            (!isCardFlipped && settingsState.questionLocale == SettingLocale.ES)
                                if (shouldSpeakSpanish) {
                                    textToSpeechInSpanish.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, "")
                                } else {
                                    textToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, "")
                                }
                            },
                            onNavigateBack = navController::popBackStack,
                        )
                    }
                    composable(
                        route = "${Routes.SETTINGS.name}/{${NavArgs.LOCALE}}",
                        arguments = listOf(
                            navArgument(name = NavArgs.LOCALE.name) {
                                type = NavType.StringType
                            },
                        ),
                    ) {
                        SettingsScreen(onNavigateBack = navController::popBackStack)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        if (::textToSpeechInSpanish.isInitialized) {
            textToSpeechInSpanish.stop()
            textToSpeechInSpanish.shutdown()
        }
        super.onDestroy()
    }

    private fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.US)
            textToSpeech.setSpeechRate(1f)
            textToSpeech.setPitch(0.8f)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language is not installed", Toast.LENGTH_SHORT).show()
                val installIntent = Intent()
                installIntent.action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
                startActivity(installIntent)
            }
        }
    }

    private fun onInitSpanish(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeechInSpanish.setLanguage(Locale(SettingLocale.ES.name.lowercase(), SettingLocale.ES.name))
            textToSpeechInSpanish.setSpeechRate(1f)
            textToSpeechInSpanish.setPitch(0.8f)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language is not installed", Toast.LENGTH_SHORT).show()
                val installIntent = Intent()
                installIntent.action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
                startActivity(installIntent)
            }
        }
    }
}