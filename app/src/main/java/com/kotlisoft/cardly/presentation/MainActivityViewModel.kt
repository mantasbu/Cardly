package com.kotlisoft.cardly.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kotlisoft.cardly.domain.preferences.Preferences
import com.kotlisoft.cardly.presentation.ui.settings.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val preferences: Preferences,
) : ViewModel() {

    private val _state = mutableStateOf(SettingsState())
    val state: State<SettingsState> = _state

    fun loadSettings() {
        val settings = preferences.loadSettings()
        _state.value = _state.value.copy(
            isSampleDataImported = settings.isSampleDataImported,
            questionLocale = settings.questionLocale,
            answerLocale = settings.answerLocale,
        )
    }
}