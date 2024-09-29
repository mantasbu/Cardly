package com.kotlisoft.cardly.presentation.ui.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlisoft.cardly.domain.model.Card
import com.kotlisoft.cardly.domain.model.Deck
import com.kotlisoft.cardly.domain.preferences.Preferences
import com.kotlisoft.cardly.domain.use_case.CardUseCases
import com.kotlisoft.cardly.domain.use_case.DataImportUseCase
import com.kotlisoft.cardly.domain.use_case.DeckUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val cardUseCases: CardUseCases,
    private val deckUseCases: DeckUseCases,
    private val dataImportUseCase: DataImportUseCase,
    private val preferences: Preferences,
) : ViewModel() {

    private val _state = mutableStateOf(SettingsState())
    val state: State<SettingsState> = _state

    init {
        loadSettings()
    }

    fun loadSettings() {
        val settings = preferences.loadSettings()
        _state.value = _state.value.copy(
            isSampleDataImported = settings.isSampleDataImported,
            questionLocale = settings.questionLocale,
            answerLocale = settings.answerLocale,
        )
    }

    fun onEvent(event: SettingEvent) {
        when (event) {
            SettingEvent.ImportSampleData -> {
                importSampleData()
            }
            is SettingEvent.SwitchQuestionLocale -> {
                val currentLocale = preferences.loadSettings().questionLocale
                val newLocale = if (currentLocale == SettingLocale.US) SettingLocale.ES else SettingLocale.US
                preferences.saveQuestionLocale(locale = newLocale)
                _state.value = _state.value.copy(questionLocale = newLocale)
            }
            is SettingEvent.SwitchAnswerLocale -> {
                val currentLocale = preferences.loadSettings().answerLocale
                val newLocale = if (currentLocale == SettingLocale.US) SettingLocale.ES else SettingLocale.US
                preferences.saveAnswerLocale(locale = newLocale)
                _state.value = _state.value.copy(answerLocale = newLocale)
            }
            SettingEvent.DeleteAllData -> {
                _state.value = _state.value.copy(isDeleteAllDataDialogVisible = true)
            }
            SettingEvent.CancelDeleteAllData -> {
                _state.value = _state.value.copy(isDeleteAllDataDialogVisible = false)
            }
            SettingEvent.ConfirmDeleteAllData -> {
                _state.value = _state.value.copy(isDeleteAllDataDialogVisible = false)
                viewModelScope.launch {
                    cardUseCases.deleteAllCards()
                    deckUseCases.deleteAllDecks()
                    preferences.saveSampleDataImport(isSampleDataImported = false)
                    _state.value = _state.value.copy(isSampleDataImported = false)
                }
            }
        }
    }

    private fun importSampleData() = viewModelScope.launch {
        _state.value = _state.value.copy(isLoadingSampleData = true)
        val inputStream = dataImportUseCase.invoke()
        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)

        var line: String?
        var currentCategory = ""

        while (bufferedReader.readLine().also { line = it } != null) {
            val row: List<String> = line!!.split(",")
            if (currentCategory != row.first()) {
                currentCategory = row.first()
                deckUseCases.addDeck(
                    Deck(name = currentCategory)
                )
            }
            cardUseCases.addCard(
                deckName = currentCategory,
                card = Card(
                    question = row[1],
                    answer = row.last(),
                ),
            )
        }

        preferences.saveSampleDataImport(isSampleDataImported = true)

        _state.value = _state.value.copy(isLoadingSampleData = false)
        _state.value = _state.value.copy(isSampleDataImported = true)
    }
}