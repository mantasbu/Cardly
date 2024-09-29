package com.kotlisoft.cardly.domain.use_case

import com.kotlisoft.cardly.domain.preferences.Preferences
import com.kotlisoft.cardly.presentation.ui.settings.SettingLocale
import javax.inject.Inject

class UpdateQuestionLocale @Inject constructor(
    private val preferences: Preferences,
) {
    operator fun invoke(locale: SettingLocale) {
        preferences.saveQuestionLocale(locale)
    }
}